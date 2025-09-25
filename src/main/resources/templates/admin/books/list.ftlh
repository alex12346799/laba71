<#import "../../layout.ftlh" as main>

<@main.layout title="Админ — Книги">

    <h3>Книги</h3>

    <!-- Фильтры -->
    <form method="get" class="row g-2 mb-3">
        <div class="col-sm-4">
            <input name="q" value="${(q)!}" class="form-control" placeholder="Название или автор">
        </div>
        <div class="col-sm-2">
            <input name="year" value="${(year)!}" class="form-control" placeholder="Год"
                   inputmode="numeric" pattern="[0-9]{4}">
        </div>
        <div class="col-sm-3">
            <select name="category" class="form-select">
                <option value="">Все категории</option>
                <#list categories as c>
                    <option value="${c.id}" ${(category?? && category==c.id)?string('selected','')}>${c.name}</option>
                </#list>
            </select>
        </div>
        <div class="col-sm-2">
            <button class="btn btn-primary w-100">Фильтр</button>
        </div>
    </form>

    <!-- Добавление -->
    <form method="post" action="/admin/books" class="row g-2 mb-4">
        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <div class="col-sm-3"><input name="title" class="form-control" placeholder="Название" required></div>
        <div class="col-sm-3"><input name="author" class="form-control" placeholder="Автор" required></div>
        <div class="col-sm-2"><input name="publicationYear" class="form-control" placeholder="Год" pattern="[0-9]{4}"></div>
        <div class="col-sm-2">
            <select name="categoryId" class="form-select" required>
                <#list categories as c>
                    <option value="${c.id}">${c.name}</option>
                </#list>
            </select>
        </div>
        <div class="col-sm-2">
            <button class="btn btn-success w-100" type="submit">Добавить</button>
        </div>
    </form>

    <!-- Таблица книг -->
    <table class="table table-striped table-hover align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Обложка</th>
            <th>Название</th>
            <th>Автор</th>
            <th>Категория</th>
            <th>Год</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list pageDto.content as book>
            <tr>
                <td>${book.id}</td>
                <td>
                    <#if book.imageUrl??>
                        <img src="/api/image/${book.id}" style="height:50px">
                    <#else>
                        <span class="text-muted">нет</span>
                    </#if>
                </td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.categoryName}</td>
                <td>${book.publicationYear!''}</td>
                <td>
                    <#if book.available>
                        <span class="badge bg-success">Доступна</span>
                    <#else>
                        <span class="badge bg-danger">Занята</span>
                        <#if book.expectedAvailableAt??>
                            <div class="small text-muted">до ${book.expectedAvailableAt}</div>
                        </#if>
                    </#if>
                </td>
                <td>
                    <!-- Загрузка обложки -->
                    <form method="post" enctype="multipart/form-data" action="/admin/books/${book.id}/image" class="d-inline">
                        <#if _csrf??>
                            <input
                                    type="hidden"
                                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                                    value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <input type="file" name="image" class="form-control form-control-sm mb-1" required>
                        <button class="btn btn-sm btn-outline-secondary w-100">Обложка</button>
                    </form>
                    <!-- Удаление -->
                    <form method="post" action="/admin/books/${book.id}/delete" class="d-inline">
                        <#if _csrf??>
                            <input
                                    type="hidden"
                                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                                    value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <button class="btn btn-sm btn-outline-danger w-100" onclick="return confirm('Удалить?')">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <!-- Пагинация -->
    <nav aria-label="Страницы">
        <ul class="pagination">
            <#list 0..(pageDto.totalPages-1) as p>
                <li class="page-item ${(p==pageDto.size)?string('active','')}">
                    <a class="page-link"
                       href="?page=${p}&size=${pageDto.size}<#if q??>&q=${q}</#if><#if year??>&year=${year}</#if><#if category??>&category=${category}</#if>">
                        ${p+1}
                    </a>
                </li>
            </#list>
        </ul>
    </nav>

</@main.layout>
