<#import "../../layout.ftlh" as main>

<@main.layout title="Заявки на получение книг">
    <h3 class="mb-4">Новые заявки читателей</h3>
    <#if requestMessage??>
        <div class="alert alert-success">${requestMessage}</div>
    </#if>
    <#if requestError??>
        <div class="alert alert-danger">${requestError}</div>
    </#if>

    <#if requests?? && (requests?size > 0)>
        <table class="table table-hover align-middle">
            <thead class="table-light">
            <tr>
                <th scope="col">Книга</th>
                <th scope="col">Читатель</th>
                <th scope="col">Вернуть до</th>
                <th scope="col">Создана</th>
                <th scope="col" class="text-end">Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list requests as request>
                <tr>
                    <td>
                        <div class="fw-semibold">${request.title!"Без названия"}</div>
                        <div class="text-muted small">${request.author!""}</div>
                        <#if request.category??>
                            <span class="badge bg-secondary-subtle text-secondary-emphasis mt-1">${request.category}</span>
                        </#if>
                    </td>
                    <td>
                        <div class="fw-semibold">${request.readerName!"Не указано"}</div>
                        <div class="text-muted small">Билет: ${request.libraryCardNumber!"—"}</div>
                    </td>
                    <td>${request.requestedDueDate!''}</td>
                    <td>${request.createdAt!''}</td>
                    <td class="text-end">
                        <form method="post" action="/admin/requests/${request.id}/approve" class="d-inline">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}" />
                            </#if>
                            <button type="submit" class="btn btn-sm btn-success">Одобрить</button>
                        </form>
                        <form method="post" action="/admin/requests/${request.id}/decline" class="d-inline ms-2">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}" />
                            </#if>
                            <button type="submit" class="btn btn-sm btn-outline-danger">Отклонить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <div class="alert alert-secondary">Нет заявок, ожидающих рассмотрения.</div>
    </#if>
</@main.layout>