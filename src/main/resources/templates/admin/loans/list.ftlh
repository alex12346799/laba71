<#import "../../layout.ftlh" as main>

<@main.layout title="Админ — Займы">

    <h3>Займы</h3>

    <!-- Фильтры -->
    <form method="get" class="card mb-4">
        <div class="card-body">
            <div class="row g-2">
                <div class="col-sm-3">
                    <input name="libraryCardNumber" value="${(filter.libraryCardNumber)!}" class="form-control"
                           placeholder="Читательский номер">
                </div>
                <div class="col-sm-2">
                    <input name="bookId" value="${(filter.bookId)!}" class="form-control"
                           placeholder="ID книги" inputmode="numeric" pattern="[0-9]+">
                </div>
                <div class="col-sm-2">
                    <select name="status" class="form-select">
                        <option value="" ${((filter.status!"") == "")?string("selected","")}>Любой статус</option>
                        <option value="EXPECTED" ${((filter.status!"") == "EXPECTED")?string("selected","")}>Ожидается
                        </option>
                        <option value="OVERDUE" ${((filter.status!"") == "OVERDUE")?string("selected","")}>Просрочен
                        </option>
                        <option value="RETURNED" ${((filter.status!"") == "RETURNED")?string("selected","")}>Возвращён
                        </option>
                    </select>

                </div>
                <div class="col-sm-2">
                    <input type="date" name="fromDate" value="${(filter.fromDate)!}" class="form-control"
                           placeholder="С даты">
                </div>
                <div class="col-sm-2">
                    <input type="date" name="toDate" value="${(filter.toDate)!}" class="form-control"
                           placeholder="По дату">
                </div>
                <div class="col-sm-1 d-grid">
                    <button class="btn btn-primary">Фильтр</button>
                </div>
            </div>
        </div>
    </form>

    <#function asDate v>
        <#if v?is_date>
            <#return v>
        <#elseif v?is_string>
            <#return v?date("yyyy-MM-dd")>
        <#else>
            <#return v?date>
        </#if>
    </#function>

    <!-- Таблица займов -->
    <table class="table table-striped table-hover align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Читатель</th>
            <th>Книга</th>
            <th>Даты</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#if pageDto?? && pageDto.content?? && (pageDto.content?size > 0)>
            <#list pageDto.content as it>
                <tr>
                    <!-- ID займа -->
                    <td>
                        <#if it.loan?? && it.loan.id??>
                            ${it.loan.id}
                        <#else>
                            ${it.loanId!}
                        </#if>
                    </td>

                    <!-- Читатель -->
                    <td>
                        <div class="fw-semibold">
                            ${it.readerName!'не указано'}
                        </div>
                        <div class="small text-muted">
                            ${it.libraryCardNumber!'—'}
                        </div>
                    </td>


                    <!-- Книга -->
                    <td>
                        <div class="fw-semibold">
                            <#if it.bookInfo?? && it.bookInfo.title??>
                                ${it.bookInfo.title}
                            <#elseif it.title??>
                                ${it.title}
                            <#elseif it.loan?? && it.loan.bookTitle??>
                                ${it.loan.bookTitle}
                            <#else>
                                <span class="text-muted">—</span>
                            </#if>
                        </div>
                        <div class="small text-muted">
                            <#if it.bookInfo?? && it.bookInfo.author??>
                                ${it.bookInfo.author}
                            <#elseif it.author??>
                                ${it.author}
                            <#elseif it.loan?? && it.loan.bookAuthor??>
                                ${it.loan.bookAuthor}
                            <#else>
                                —
                            </#if>
                            <#-- bookId -->
                            <#assign _bookId =
                            (it.bookInfo?? && it.bookInfo.id??)?then(it.bookInfo.id,
                            (it.bookId??)?then(it.bookId,
                            (it.loan?? && it.loan.bookId??)?then(it.loan.bookId, null))) >
                            <#if _bookId??>
                                <span class="text-muted"> · ID: ${_bookId}</span>
                            </#if>
                        </div>
                    </td>

                    <!-- Даты -->
                    <td>
                        <div>Выдано:
                            <#if it.loan?? && it.loan.borrowDate??>
                                ${asDate(it.loan.borrowDate)}
                            <#elseif it.borrowDate??>
                                ${asDate(it.borrowDate)}
                            <#else>—</#if>
                        </div>
                        <div>До:
                            <#if it.loan?? && it.loan.dueDate??>
                                ${asDate(it.loan.dueDate)}
                            <#elseif it.dueDate??>
                                ${asDate(it.dueDate)}
                            <#else>—</#if>
                        </div>
                        <div class="small text-muted">Возврат:
                            <#if it.loan?? && it.loan.returnedAt??>
                                ${asDate(it.loan.returnedAt)}
                            <#elseif it.returnedAt??>
                                ${asDate(it.returnedAt)}
                            <#else>—</#if>
                        </div>
                    </td>


                    <!-- Статус -->
                    <td>
                        <#assign _status =
                        (it.loan?? && it.loan.status??)?then(it.loan.status,
                        (it.status??)?then(it.status, "")) />

                        <#if _status == "RETURNED">
                            <span class="badge bg-secondary">Возвращён</span>
                        <#elseif _status == "OVERDUE">
                            <span class="badge bg-danger">Просрочен</span>
                        <#elseif _status == "EXPECTED">
                            <span class="badge bg-primary">Ожидается</span>
                        <#else>
                            <span class="badge bg-light text-muted">—</span>
                        </#if>

                        <#assign _ret = "">
                        <#if it.loan?? && it.loan.returnedAt??>
                            <#assign _ret = asDate(it.loan.returnedAt)>
                        <#elseif it.returnedAt??>
                            <#assign _ret = asDate(it.returnedAt)>
                        </#if>

                        <#assign _due = "">
                        <#if it.loan?? && it.loan.dueDate??>
                            <#assign _due = asDate(it.loan.dueDate)>
                        <#elseif it.dueDate??>
                            <#assign _due = asDate(it.dueDate)>
                        </#if>

                        <#-- Просрочка: нет возврата и сегодня > dueDate -->
                        <#if (!_ret??) && (_due??) && (.now?date > _due)>
                            <div class="small text-danger mt-1">Просрочен</div>
                        </#if>
                    </td>


                    <!-- Действия -->
                    <td>
                        <#-- Кнопка возврата показываем только если НЕ возвращён -->
                        <#if _status != "RETURNED">
                            <form method="post" action="/admin/loans/return" class="d-flex gap-2 align-items-center">

                                <#if _csrf??>
                                    <input
                                            type="hidden"
                                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                                            value="${(_csrf.token)!'csrf-token'}" />
                                </#if>
                                <input type="hidden" name="loanId"
                                       value="<#if it.loan?? && it.loan.id??>${it.loan.id}<#else>${it.loanId!}</#if>">
                                <input type="date" name="returnedAt" class="form-control form-control-sm"
                                       value="${.now?string('yyyy-MM-dd')}" required>
                                <button class="btn btn-sm btn-outline-success">Вернуть книгу</button>
                            </form>
                        <#else>
                            <span class="text-muted">—</span>
                        </#if>
                    </td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="6" class="text-center text-muted">Записей не найдено</td>
            </tr>
        </#if>
        </tbody>
    </table>

    <!-- Пагинация -->
    <#assign _page  = (pageDto.page!0)?int>
    <#assign _size  = (pageDto.size!20)?int>
    <#assign _pages = (pageDto.totalPages!0)?int>

    <#if (_pages > 1)>
        <nav aria-label="Страницы" class="mt-3">
            <ul class="pagination">
                <#list 0..(_pages-1) as p>
                    <li class="page-item ${(p == _page)?string('active','')}">
                        <a class="page-link"
                           href="?page=${p}&size=${_size}
                 <#if filter.libraryCardNumber?has_content>&libraryCardNumber=${filter.libraryCardNumber}</#if>
                 <#if filter.bookId?has_content>&bookId=${filter.bookId}</#if>
                 <#if filter.status?has_content>&status=${filter.status}</#if>
                 <#if filter.fromDate?has_content>&fromDate=${filter.fromDate}</#if>
                 <#if filter.toDate?has_content>&toDate=${filter.toDate}</#if>">
                            ${p + 1}
                        </a>
                    </li>
                </#list>
            </ul>
        </nav>
    </#if>

</@main.layout>
