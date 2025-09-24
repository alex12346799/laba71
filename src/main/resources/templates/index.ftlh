<#import "layout.ftlh" as main>

<@main.layout>

    <div class="row g-4">
        <!-- Sidebar: Жанры -->
        <aside class="col-12 col-lg-3">
            <div class="card genre-sidebar">
                <div class="card-header fw-semibold">Жанры</div>
                <div class="list-group list-group-flush">
                    <a href="/" class="list-group-item list-group-item-action ${((param.category)??)?string('','active')}">Все</a>
                    <#if categories?? && (categories?size > 0)>
                        <#list categories as category>
                            <a href="?category=${category.id}" class="list-group-item list-group-item-action">
                                ${category.name}
                            </a>
                        </#list>
                    </#if>
                </div>
            </div>
        </aside>

        <!-- Content: Карточки книг -->
        <section class="col-12 col-lg-9">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 g-3">
                <#if bookList?? && (bookList?size > 0)>
                    <#list bookList as book>
                        <div class="col">
                            <div class="card h-100 book-card shadow-sm">
                                <img src="/api/image/${book.id}" alt="${book.title}">
                                <div class="card-body">
                                    <h6 class="card-subtitle text-body-secondary mb-1">${book.author}</h6>
                                    <h5 class="card-title">«${book.title}»</h5>
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

            <!-- Пагинация (заглушка) -->
            <nav class="mt-4" aria-label="Навигация страниц">
                <ul class="pagination justify-content-center">
                    <li class="page-item disabled"><span class="page-link">Назад</span></li>
                    <li class="page-item active" aria-current="page"><span class="page-link">1</span></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">Вперёд</a></li>
                </ul>
            </nav>
        </section>
    </div>

</@main.layout>