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
        </style>
    </head>
    <body>

    <!-- header -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
        <div class="container">
            <a class="navbar-brand fw-semibold" href="/">Литература</a>
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="/auth/profile">Мои книги</a></li>
            </ul>

            <form class="d-none d-lg-flex me-2" role="search" style="min-width:320px;">
                <input class="form-control" type="search" placeholder="Поиск по книгам, авторам…" aria-label="Search">
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
    </body>
    </html>
</#macro>