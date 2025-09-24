<#--<#import "spring.ftl" as spring>-->

<#--<#macro layout title="Литература">-->
<#--    <!DOCTYPE html>-->
<#--    <html lang="ru">-->
<#--    <head>-->
<#--        <meta charset="UTF-8">-->
<#--        <title>${title}</title>-->
<#--        <meta name="viewport" content="width=device-width, initial-scale=1">-->
<#--        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">-->
<#--        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">-->
<#--        <style>-->
<#--            body{background-color:#f7f7f9}-->
<#--            .genre-sidebar{position:sticky;top:1rem}-->
<#--            .book-card img{object-fit:cover;width:100%;height:140px;background:#e9ecef}-->
<#--            .status-warn{color:#0d6efd;text-decoration:none}-->
<#--            .status-warn:hover{text-decoration:underline}-->
<#--        </style>-->
<#--    </head>-->
<#--    <body>-->

<#--    <!-- header &ndash;&gt;-->
<#--    <nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">-->
<#--        <div class="container">-->
<#--            <a class="navbar-brand fw-semibold" href="/">Литература</a>-->
<#--            <ul class="navbar-nav me-auto">-->
<#--                <li class="nav-item"><a class="nav-link" href="/profile/books">Мои книги</a></li>-->
<#--            </ul>-->

<#--            <form class="d-none d-lg-flex me-2" role="search" style="min-width:320px;">-->
<#--                <input class="form-control" type="search" placeholder="Поиск по книгам, авторам…" aria-label="Search">-->
<#--            </form>-->

<#--            <div class="d-flex gap-2">-->
<#--                <#if (currentUser)??>-->
<#--                    <span class="navbar-text me-2 small text-muted">${currentUser}</span>-->
<#--                    <a class="btn btn-outline-secondary" href="/auth/logout">Выйти</a>-->
<#--                <#else>-->
<#--                    <a class="btn btn-outline-secondary" href="/auth/login">Авторизация</a>-->
<#--                    <a class="btn btn-primary" href="/auth/register">Регистрация</a>-->
<#--                </#if>-->
<#--            </div>-->
<#--        </div>-->
<#--    </nav>-->

<#--    <main class="container my-4">-->
<#--        <#nested>-->
<#--    </main>-->

<#--    <!-- footer &ndash;&gt;-->
<#--    <footer class="py-4 border-top bg-white">-->
<#--        <div class="container small text-muted">© Библиотека, 2025</div>-->
<#--    </footer>-->

<#--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>-->
<#--    </body>-->
<#--    </html>-->
<#--</#macro>-->


<#import "spring.ftl" as spring>

<#macro layout title="Литература">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body{background-color:#f7f7f9}
            .genre-sidebar{position:sticky;top:1rem}
            .book-card img{object-fit:cover;width:100%;height:140px;background:#e9ecef}
            .status-warn{color:#0d6efd;text-decoration:none}
            .status-warn:hover{text-decoration:underline}
            /* live search */
            .search-wrap{position:relative; min-width:320px;}
            #searchResults{
                display:none; position:absolute; top:100%; left:0; right:0; z-index:1050;
                max-height:380px; overflow:auto; background:#fff; border:1px solid #dee2e6;
                border-radius:.5rem; box-shadow:0 .5rem 1rem rgba(0,0,0,.15);
            }
            #searchResults .list-group-item{border:0; border-bottom:1px solid #f1f3f5}
            #searchResults .list-group-item:last-child{border-bottom:0}
        </style>
    </head>
    <body>

    <!-- header -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
        <div class="container">
            <a class="navbar-brand fw-semibold" href="/">Литература</a>
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="/profile/books">Мои книги</a></li>
            </ul>

            <!-- live user search -->
            <form class="d-none d-lg-flex me-2 search-wrap" role="search" onsubmit="return false;">
                <input id="userSearch" class="form-control" type="search" placeholder="Поиск пользователей…" autocomplete="off" aria-label="Search users">
                <div id="searchResults" class="list-group"></div>
            </form>

            <div class="d-flex gap-2">
                <#if (currentUser)??>
                    <span class="navbar-text me-2 small text-muted">${currentUser}</span>
                    <a class="btn btn-outline-secondary" href="/auth/logout">Выйти</a>
                <#else>
                    <a class="btn btn-outline-secondary" href="/auth/login">Авторизация</a>
                    <a class="btn btn-primary" href="/auth/register">Регистрация</a>
                </#if>
            </div>
        </div>
    </nav>

    <main class="container my-4">
        <#nested>
    </main>

    <!-- footer -->
    <footer class="py-4 border-top bg-white">
        <div class="container small text-muted">© Библиотека, 2025</div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (() => {
            const input = document.getElementById('userSearch');
            const resultsBox = document.getElementById('searchResults');
            if (!input || !resultsBox) return;

            const CSRF = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
            const CSRF_HEADER = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

            let timer = null;
            const DEBOUNCE = 200;

            function showResults(){ resultsBox.style.display = 'block'; }
            function hideResults(){ resultsBox.style.display = 'none'; }
            function htmlEscape(s){ return s.replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m])); }

            async function search(q){
                const url = '/api/users/search?q=' + encodeURIComponent(q);
                const opts = { headers: {} };
                if (CSRF && CSRF_HEADER) opts.headers[CSRF_HEADER] = CSRF;

                const res = await fetch(url, opts).catch(() => null);
                if (!res || !res.ok) return [];
                return res.json().catch(() => []);
            }

            input.addEventListener('input', () => {
                const q = input.value.trim();
                clearTimeout(timer);
                if (!q) { hideResults(); resultsBox.innerHTML = ''; return; }

                timer = setTimeout(async () => {
                    const users = await search(q);

                    if (!users.length){
                        resultsBox.innerHTML = '<div class="list-group-item text-muted">Нет пользователей</div>';
                        showResults();
                        return;
                    }

                    let html = '';
                    for (let i=0;i<users.length;i++){
                        const u = users[i];
                        const id = u.id;
                        const name = htmlEscape(String(u.displayName ?? ''));
                        const username = htmlEscape(String(u.username ?? ''));
                        const avatar = u.avatar ? ('/api/images/avatar/' + id) : '/static/img/default-avatar.png';

                        html += '<a href="/auth/profile/' + id + '" class="list-group-item list-group-item-action d-flex align-items-center">'
                            +   '<img src="' + avatar + '" alt="" style="width:32px;height:32px;border-radius:50%;object-fit:cover;margin-right:10px;">'
                            +   '<div>'
                            +     '<div class="fw-bold">' + name + '</div>'
                            +     '<div class="text-muted small">@' + username + '</div>'
                            +   '</div>'
                            + '</a>';
                    }
                    resultsBox.innerHTML = html;
                    showResults();
                }, DEBOUNCE);
            });

            document.addEventListener('click', (e) => {
                if (!resultsBox.contains(e.target) && e.target !== input) hideResults();
            });

            // клавиатура: ESC скрыть
            input.addEventListener('keydown', (e) => {
                if (e.key === 'Escape') { hideResults(); input.blur(); }
            });
        })();
    </script>
    </body>
    </html>
</#macro>
