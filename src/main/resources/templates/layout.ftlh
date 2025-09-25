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
            .search-wrap{position:relative; min-width:320px;}
            #bookResults{
                display:none; position:absolute; top:100%; left:0; right:0; z-index:1050;
                max-height:380px; overflow:auto; background:#fff; border:1px solid #dee2e6;
                border-radius:.5rem; box-shadow:0 .5rem 1rem rgba(0,0,0,.15);
            }
            #bookResults .list-group-item{border:0; border-bottom:1px solid #f1f3f5}
            #bookResults .list-group-item:last-child{border-bottom:0}
        </style>
    </head>
    <body>

    <nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
        <div class="container">
            <a class="navbar-brand fw-semibold" href="/">Литература</a>
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="/auth/profile">Мои книги</a></li>
            </ul>

            <form class="d-none d-lg-flex me-2 search-wrap" role="search" onsubmit="return false;">
                <input id="bookSearch" class="form-control" type="search"
                       placeholder="Поиск: название, автор или год…"
                       autocomplete="off" aria-label="Search books">
                <div id="bookResults" class="list-group"></div>
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
            const input = document.getElementById('bookSearch');
            const box   = document.getElementById('bookResults');
            if (!input || !box) return;

            let timer = null;
            const DEBOUNCE = 200;

            function show(){ box.style.display = 'block'; }
            function hide(){ box.style.display = 'none'; }
            function esc(s){ return String(s ?? '').replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[m])); }

            async function searchBooks(q){
                const url = '/api/books/search?q='+encodeURIComponent(q);
                const res = await fetch(url).catch(()=>null);
                if (!res || !res.ok) return [];
                try { return await res.json(); } catch { return []; }
            }

            input.addEventListener('input', () => {
                const q = input.value.trim();
                clearTimeout(timer);
                if (!q){ hide(); box.innerHTML=''; return; }

                timer = setTimeout(async () => {
                    const items = await searchBooks(q);
                    if (!items.length){
                        box.innerHTML = '<div class="list-group-item text-muted">Ничего не найдено</div>';
                        show(); return;
                    }

                    let html = '';
                    for (let i=0;i<items.length;i++){
                        const b = items[i];
                        const href   = '/book/'+ b.id;
                        const imgSrc = ('/api/image/'+b.id);
                        const title  = esc(b.title);
                        const author = esc(b.author ?? '');

                        html += '<a href="'+href+'" class="list-group-item list-group-item-action d-flex">'
                            +   '<img src="'+imgSrc+'" alt=""'
                            +        ' style="width:40px;height:56px;object-fit:cover;border-radius:.25rem;margin-right:10px;background:#e9ecef;">'
                            +   '<div class="d-flex flex-column">'
                            +     '<div class="fw-semibold">'+ title +'</div>'
                            +     '<div class="text-muted small">'+ author +'</div>'
                            +   '</div>'
                            + '</a>';
                    }
                    box.innerHTML = html;
                    show();
                }, DEBOUNCE);
            });

            document.addEventListener('click', (e) => {
                if (!box.contains(e.target) && e.target !== input) hide();
            });

            input.addEventListener('keydown', (e) => {
                if (e.key === 'Escape'){ hide(); input.blur(); }
            });
        })();
    </script>

    </body>
    </html>
</#macro>
