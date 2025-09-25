<#import "layout.ftlh" as main>

<@main.layout title="Литература — Главная">
    <!-- Поиск/фильтры -->
    <form method="get" class="row g-2 mb-3">
        <input type="hidden" name="category" value="${(currentCategory)!}">
        <div class="col-sm-5">
            <input name="q" value="${(q)!}" class="form-control" placeholder="Название или автор">
        </div>
        <div class="col-sm-2">
            <input name="year" value="${(year)!}" class="form-control" placeholder="Год"
                   inputmode="numeric" pattern="[0-9]{4}">

        </div>
        <div class="col-sm-3">
            <select name="sort" class="form-select">
                <option value=""         ${((sort!"") == "")?string('selected','')}>По названию (A→Z)</option>
                <option value="yearAsc"  ${((sort!"") == "yearAsc")?string('selected','')}>По году (возр.)</option>
                <option value="yearDesc" ${((sort!"") == "yearDesc")?string('selected','')}>По году (убыв.)</option>
            </select>
        </div>
        <div class="col-sm-2">
            <button class="btn btn-primary w-100" type="submit">Найти</button>
        </div>
    </form>

    <div class="row g-4">
        <!-- Sidebar: Категории с динамической “Показать ещё” -->
        <aside class="col-12 col-lg-3">
            <div class="list-group list-group-flush" id="catList" data-step="10">
                <a href="/?page=0&size=${(pageDto.size!12)}<#if q??>&q=${q?url}</#if><#if year??>&year=${year}</#if><#if sort??>&sort=${sort}</#if>"
                   class="list-group-item list-group-item-action ${ (currentCategory??)?string('','active') }">Все</a>

                <#if categories?? && (categories?size > 0)>
                    <#list categories as category>
                        <#assign idx = category?index>
                        <a href="/?category=${category.id}&page=0&size=${(pageDto.size!12)}<#if q??>&q=${q?url}</#if><#if year??>&year=${year}</#if><#if sort??>&sort=${sort}</#if>"
                           class="list-group-item list-group-item-action ${(currentCategory?? && currentCategory == category.id)?string('active','')}"
                           style="${(idx >= 10)?string('display:none;','')}">
                            ${category.name}
                        </a>
                    </#list>
                    <#if categories?? && (categories?size > 10)>
                        <button id="catMore" type="button" class="list-group-item list-group-item-action text-center">
                            Показать ещё
                        </button>
                    </#if>
                </#if>
            </div>
        </aside>

        <!-- Контент: карточки книг -->
        <section class="col-12 col-lg-9">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 g-3">
                <#if pageDto.content?? && (pageDto.content?size > 0)>
                    <#list pageDto.content as book>
                        <div class="col">
                            <div class="card h-100 book-card shadow-sm d-flex flex-column">
                                <img src="/api/image/${book.id}" alt="${book.title}">
                                <div class="card-body flex-grow-1">
                                    <h6 class="card-subtitle text-body-secondary mb-1">${book.author}</h6>
                                    <h5 class="card-title">«${book.title}»</h5>
                                </div>
                                <div class="card-footer bg-transparent border-0 mt-auto">
                                    <div class="d-grid">
                                        <button class="btn btn-outline-primary">
                                            <i class="bi bi-box-arrow-in-down"></i> Получить
                                        </button>
                                    <div class="d-grid mt-3">

                                        <form action="/books/${book.id}/borrow" method="post">
                                            <#if _csrf??>
                                                <input
                                                        type="hidden"
                                                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                        value="${(_csrf.token)!'csrf-token'}"/>
                                            </#if>
                                            <button type="submit" class="btn btn-outline-primary">
                                                <i class="bi bi-box-arrow-in-down"></i> Получить
                                            </button>
                                        </form>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </#list>
                <#else>
                    <div class="col"><div class="alert alert-secondary">Книг не найдено</div></div>
                </#if>
            </div>

            <!-- Пагинация -->
            <#assign curPage    = (pageDto.page!0)>
            <#assign pageSize   = (pageDto.size!12)>
            <#assign totalPages = (pageDto.totalPages!0)>
            <#assign isLast     = (pageDto.last!true)>

            <#if pageDto?? && pageDto.totalPages?? && (pageDto.totalPages > 1)>
                <#assign prevPage = (curPage > 0)?then(curPage - 1, 0)>
                <#assign nextPage = (curPage < totalPages - 1)?then(curPage + 1, curPage)>

                <nav class="mt-4" aria-label="Навигация страниц">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${(curPage == 0)?string('disabled','')}">
                            <a class="page-link"
                               href="/?page=${prevPage}&size=${pageSize}<#if currentCategory??>&category=${currentCategory}</#if><#if q??>&q=${q?url}</#if><#if year??>&year=${year}</#if><#if sort??>&sort=${sort}</#if>">Назад</a>
                        </li>

                        <#list 0..(totalPages - 1) as p>
                            <li class="page-item ${(p == curPage)?string('active','')}">
                                <a class="page-link"
                                   href="/?page=${p}&size=${pageSize}<#if currentCategory??>&category=${currentCategory}</#if><#if q??>&q=${q?url}</#if><#if year??>&year=${year}</#if><#if sort??>&sort=${sort}</#if>">
                                    ${p + 1}
                                </a>
                            </li>
                        </#list>

                        <li class="page-item ${isLast?string('disabled','')}">
                            <a class="page-link"
                               href="/?page=${nextPage}&size=${pageSize}<#if currentCategory??>&category=${currentCategory}</#if><#if q??>&q=${q?url}</#if><#if year??>&year=${year}</#if><#if sort??>&sort=${sort}</#if>">Вперёд</a>
                        </li>
                    </ul>
                </nav>
            </#if>
        </section>
    </div>

    <script>
        (function(){
            const box = document.getElementById('catList');
            if(!box) return;
            const step = parseInt(box.dataset.step||'10',10);
            const moreBtn = document.getElementById('catMore');
            if(!moreBtn) return;

            function revealNext(){
                const hidden = Array.from(box.querySelectorAll('a.list-group-item[style*="display:none"]'));
                if(hidden.length===0){ moreBtn.remove(); return; }
                hidden.slice(0, step).forEach(el => el.style.display = '');
                if(hidden.length - step <= 0) moreBtn.remove();
            }
            moreBtn.addEventListener('click', revealNext);
        })();
    </script>
</@main.layout>
