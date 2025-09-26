<#import "layout.ftlh" as main>

<@main.layout title="Библиотека">
    <#if loanRequestError??>
        <div class="alert alert-danger mb-3">${loanRequestError}</div>
    </#if>
    <#if loanRequestSuccess??>
        <div class="alert alert-success mb-3">${loanRequestSuccess}</div>
    </#if>

    <form method="get" class="row g-2 mb-4">
        <input type="hidden" name="category" value="">
        <div class="col-sm-5">
            <input name="q" value="" class="form-control" placeholder="Название или автор">
        </div>
        <div class="col-sm-2">
            <input name="year" value="" class="form-control" placeholder="Год"
                   inputmode="numeric" pattern="[0-9]{4}">
        </div>
        <div class="col-sm-3">
            <select name="sort" class="form-select">
                <option value="">По алфавиту (А→Я)</option>
                <option value="yearAsc">По году (старые вперёд)</option>
                <option value="yearDesc">По году (новые вперёд)</option>
            </select>
        </div>
        <div class="col-sm-2">
            <button class="btn btn-primary w-100" type="submit">Применить</button>
        </div>
    </form>

    <div class="row g-4">
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

        <section class="col-12 col-lg-9">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-xl-3 g-3">
                <#if pageDto.content?? && (pageDto.content?size > 0)>
                    <#list pageDto.content as book>
                        <div class="col" id="book-${book.id}">
                            <div class="card h-100 book-card shadow-sm d-flex flex-column">
                                <img src="/api/image/${book.id}" alt="${book.title}">
                                <div class="card-body d-flex flex-column">
                                    <div class="mb-2">
                                        <div class="text-muted small">${book.author}</div>
                                        <h5 class="card-title mb-2">${book.title}</h5>
                                        <#if book.categoryName?? && book.categoryName?has_content>
                                            <span class="badge bg-light text-secondary border">${book.categoryName}</span>
                                        </#if>
                                    </div>

                                    <#if book.available>
                                        <span class="badge bg-success align-self-start">Доступна</span>
                                    <#elseif book.expectedAvailableAt??>
                                        <span class="badge bg-warning text-dark align-self-start">Ожидается возврат ${book.expectedAvailableAt}</span>
                                    <#else>
                                        <span class="badge bg-secondary align-self-start">Недоступна</span>
                                    </#if>

                                    <div class="mt-auto">
                                        <#if book.available>
                                            <button type="button" class="btn btn-outline-primary w-100 mt-3"
                                                    data-action="request-book"
                                                    data-book-id="${book.id}"
                                                    data-book-title="${book.title}"
                                                    data-book-author="${book.author}"
                                                    data-bs-toggle="modal" data-bs-target="#requestModal">
                                                <i class="bi bi-journal-plus me-1"></i> Получить
                                            </button>

                                        <#elseif book.expectedAvailableAt??>
                                            <div class="btn btn-outline-secondary w-100 mt-3 disabled">
                                                Свободна с ${book.expectedAvailableAt}
                                            </div>
                                        <#else>
                                            <div class="btn btn-outline-secondary w-100 mt-3 disabled">Недоступна</div>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#list>
                <#else>
                    <div class="col">
                        <div class="alert alert-secondary">Не найдено ни одной книги.</div>
                    </div>
                </#if>
            </div>

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

    <div class="modal fade" id="requestModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Оформление заявки</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                    <form id="requestForm" action="" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </#if>
                        <input type="hidden" name="bookId" id="requestBookId" value="" />
                        <div class="mb-3">
                            <label class="form-label">Книга</label>
                            <div class="form-control-plaintext" id="requestBookTitle">—</div>
                            <div class="form-control-plaintext text-muted" id="requestBookAuthor"></div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="libraryCardNumber">Номер читательского билета</label>
                            <input type="text" class="form-control" id="libraryCardNumber" name="libraryCardNumber"
                                   value="" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="dueDate">Дата возврата</label>
                            <input type="date" class="form-control" id="dueDate" name="dueDate"
                                   value="" min="" required>
                        </div>
                        <div class="modal-footer px-0">
                            <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Отмена
                            </button>
                            <button type="submit" class="btn btn-primary">Отправить заявку</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        window.addEventListener('load', function () {
            const categoryBox = document.getElementById('catList');
            const moreBtn = document.getElementById('catMore');
            if (categoryBox && moreBtn) {
                const step = parseInt(categoryBox.dataset.step || '10', 10);
                moreBtn.addEventListener('click', () => {
                    const hidden = Array.from(categoryBox.querySelectorAll('a.list-group-item[style*="display:none"]'));
                    hidden.slice(0, step).forEach(el => el.style.display = '');
                    if (hidden.length <= step) moreBtn.remove();
                });
            }

            const requestModalElement = document.getElementById('requestModal');
            if (!requestModalElement) return;

            const modalInstance = (window.bootstrap && bootstrap.Modal)
                ? new bootstrap.Modal(requestModalElement)
                : null;

            const form        = document.getElementById('requestForm');
            const bookIdInput = document.getElementById('requestBookId');
            const titleNode   = document.getElementById('requestBookTitle');
            const authorNode  = document.getElementById('requestBookAuthor');
            const cardInput   = document.getElementById('libraryCardNumber');
            const dueInput    = document.getElementById('dueDate');
            const authenticatedCard = '${authenticatedCard!""}';

            function fillFormFromButton(btn) {
                if (!btn) return;
                const bookId = btn.dataset.bookId;
                const title  = btn.dataset.bookTitle || 'Книга';
                const author = btn.dataset.bookAuthor || '';

                form.action = '/books/' + bookId + '/request';
                bookIdInput.value = bookId;
                titleNode.textContent = title;
                authorNode.textContent = author;

                if (!cardInput.value) cardInput.value = authenticatedCard;

                const todayIso = new Date().toISOString().split('T')[0];
                dueInput.min = todayIso;
                if (!dueInput.value) {
                    const suggested = new Date();
                    suggested.setDate(suggested.getDate() + 14);
                    dueInput.value = suggested.toISOString().split('T')[0];
                }
            }

            let lastClickedBtn = null;
            document.querySelectorAll('[data-action="request-book"]').forEach(btn => {
                btn.addEventListener('click', () => {
                    lastClickedBtn = btn;
                    fillFormFromButton(btn);
                });
            });

            requestModalElement.addEventListener('show.bs.modal', () => {
                if (!bookIdInput.value) fillFormFromButton(lastClickedBtn);
            });

            const errorBookId   = '${loanRequestErrorBookId!""}';
            const successBookId = '${loanRequestSuccessBookId!""}';

            if (errorBookId) {
                const btn = document.querySelector('[data-action="request-book"][data-book-id="' + errorBookId + '"]');
                if (btn) {
                    fillFormFromButton(btn);
                    if (modalInstance) modalInstance.show();
                }
            }

            if (successBookId) {
                const target = document.getElementById('book-' + successBookId);
                if (target) target.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        });
    </script>

</@main.layout>
