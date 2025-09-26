<#--<#import "../layout.ftlh" as main>-->

<#--<@main.layout title="Админ-панель">-->
<#--    <div class="row g-3">-->
<#--        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/categories">Категории</a></div>-->
<#--        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/books">Книги</a></div>-->
<#--        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/users">Пользователи</a></div>-->
<#--        <div class="col-md-3"><a class="btn btn-primary w-100" href="/admin/loans">Выдачи</a></div>-->
<#--    </div>-->
<#--</@main.layout>-->



<#import "../layout.ftlh" as main>
<#--<#import "spring.ftl" as spring>-->

<@main.layout title="Админ-панель">
    <div class="row g-3 mb-4">
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/categories">Категории</a></div>
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/books">Книги</a></div>
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/users">Пользователи</a></div>
        <div class="col-md-3"><a class="btn btn-primary w-100" href="/admin/loans">Выдачи</a></div>
    </div>

    <h4>Заявки на выдачу книг</h4>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Книга</th>
            <th>Пользователь</th>
            <th>Дата возврата</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list pageDto.content as loan>
            <tr>
                <td>${loan.title}</td>
                <td>${loan.readerName}</td>
                <td>${loan.dueDate}</td>
                <td>${loan.status}</td>
                <td>
                    <#if loan.status == "EXPECTED">
                        <form action="/admin/loans/${loan.loanId}/approve" method="post" style="display:inline">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                            </#if>
                            <button class="btn btn-success btn-sm" type="submit">Одобрить</button>
                        </form>
                        <form action="/admin/loans/${loan.loanId}/decline" method="post" style="display:inline">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                            </#if>
                            <button class="btn btn-danger btn-sm" type="submit">Отклонить</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>
