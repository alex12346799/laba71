<#import "spring.ftl" as spring>

<#macro layout title="Библиотека">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="icon" href="/favicon.ico" type="image/x-icon">
        <style>
            body { background-color: #f7f7f9; }
            .book-card img { object-fit: cover; width: 100%; height: 160px; background: #e9ecef; }
            .genre-sidebar { position: sticky; top: 1rem; }
            .search-wrap { position: relative; min-width: 320px; }
            #bookResults {
                display: none;
                position: absolute;
                top: 100%;
                left: 0;
                right: 0;
                z-index: 1050;
                max-height: 380px;
                overflow: auto;
                background: #fff;
                border: 1px solid #dee2e6;
                border-radius: .5rem;
                box-shadow: 0 .5rem 1rem rgba(0,0,0,.15);
            }
            #bookResults .list-group-item { border: 0; border-bottom: 1px solid #f1f3f5; }
            #bookResults .list-group-item:last-child { border-bottom: 0; }
        </style>
    </head>
    <body>
    <#assign _auth        = (SPRING_SECURITY_CONTEXT.authentication)!>
    <#assign _isAuth      = (_auth?? && _auth.principal?has_content && _auth.principal != 'anonymousUser')>
    <#assign _authorities = (_auth.authorities)![]>
    <#assign _roleNames   = []>
    <#list _authorities as a>
        <#assign _roleNames = _roleNames + [(a.authority!'')?string]>
    </#list>
    <#assign _isAdmin  = _roleNames?seq_contains('ROLE_ADMIN')>
    <#assign _principal   = (_auth.principal)!>
    <#assign _displayName = (_principal.name)!((_principal.username)!'Гость')>

    <nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
        <div class="container">
            <a class="navbar-brand fw-semibold" href="/">Библиотека</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Переключить меню">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <#if _isAuth>
                        <li class="nav-item"><a class="nav-link" href="/auth/profile">Мои книги</a></li>
                    </#if>
                    <#if _isAdmin>
                        <li class="nav-item"><a class="nav-link" href="/admin">Админка</a></li>
                    </#if>
                </ul>

<#--                 <form class="d-none d-lg-flex me-3 search-wrap" role="search" onsubmit="return false;"> -->
<#--                     <input id="bookSearch" class="form-control" type="search" placeholder="Поиск по названию или автору" autocomplete="off" aria-label="Поиск книг"> -->
<#--                     <div id="bookResults" class="list-group"></div> -->
<#--                 </form> -->

                <div class="d-flex align-items-center gap-2">
                    <#if _isAuth>
                        <span class="navbar-text small text-muted">${_displayName}</span>
                        <form method="post" action="/logout" class="m-0">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                            <button class="btn btn-outline-secondary btn-sm" type="submit">Выйти</button>
                        </form>
                    <#else>
                        <a class="btn btn-outline-secondary btn-sm" href="/auth/login">Войти</a>
                        <a class="btn btn-primary btn-sm" href="/auth/register">Получить билет</a>
                    </#if>
                </div>
            </div>
        </div>
    </nav>

    <main class="container my-4">
        <#nested>
    </main>

    <footer class="py-4 border-top bg-white">
        <div class="container small text-muted">© Библиотека, 2025</div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (function () {
            const input = document.getElementById('bookSearch');
            const box = document.getElementById('bookResults');
            if (!input || !box) {
                return;
            }

            let timer = null;
            const DEBOUNCE = 200;

            function showBox() { box.style.display = 'block'; }
            function hideBox() { box.style.display = 'none'; }
            function escapeHtml(str) {
                return String(str ?? '').replace(/[&<>"']/g, function (ch) {
                    return { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[ch];
                });
            }

            async function searchBooks(query) {
                const url = '/books/' + encodeURIComponent(query);
                try {
                    const response = await fetch(url);
                    if (!response.ok) {
                        return [];
                    }
                    return await response.json();
                } catch (e) {
                    return [];
                }
            }

            input.addEventListener('input', () => {
                const q = input.value.trim();
                clearTimeout(timer);
                if (!q) {
                    hideBox();
                    box.innerHTML = '';
                    return;
                }

                timer = setTimeout(async () => {
                    const items = await searchBooks(q);
                    if (!items.length) {
                        box.innerHTML = '<div class="list-group-item text-muted">Ничего не найдено</div>';
                        showBox();
                        return;
                    }

                    let html = '';
                    items.forEach(book => {
                        const href = '/books/' + book.id + '/borrow-form';
                        const imgSrc = '/api/image/' + book.id;
                        const title = escapeHtml(book.title);
                        const author = escapeHtml(book.author ?? '');
                        html += '<a href="' + href + '" class="list-group-item list-group-item-action d-flex">'
                            + '<img src="' + imgSrc + '" alt="" style="width:40px;height:56px;object-fit:cover;border-radius:.25rem;margin-right:10px;background:#e9ecef;">'
                            + '<div class="d-flex flex-column">'
                            + '<div class="fw-semibold">' + title + '</div>'
                            + '<div class="text-muted small">' + author + '</div>'
                            + '</div>'
                            + '</a>';
                    });
                    box.innerHTML = html;
                    showBox();
                }, DEBOUNCE);
            });

            document.addEventListener('click', (event) => {
                if (!box.contains(event.target) && event.target !== input) {
                    hideBox();
                }
            });

            input.addEventListener('keydown', (event) => {
                if (event.key === 'Escape') {
                    hideBox();
                    input.blur();
                }
            });
        })();
    </script>
    </body>
    </html>
</#macro>