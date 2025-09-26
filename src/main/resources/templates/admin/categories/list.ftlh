<#import "../../layout.ftlh" as main>

<@main.layout title="Админ — Категории">

    <h3>Категории</h3>

    <!-- Форма добавления -->
    <form method="post" action="/admin/categories" class="row g-2 mb-4">
        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <div class="col-sm-8">
            <input type="text" name="name" class="form-control" placeholder="Название категории" required>
        </div>
        <div class="col-sm-4">
            <button class="btn btn-success w-100" type="submit">Добавить</button>
        </div>
    </form>

    <!-- Таблица -->
    <table class="table table-striped table-hover align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#if items?? && (items?size > 0)>
            <#list items as c>
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>
                        <!-- Удаление -->
                        <form method="post" action="/admin/categories/${c.id}/delete" class="d-inline">
                            <#if _csrf??>
                                <input
                                        type="hidden"
                                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                                        value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <button class="btn btn-sm btn-outline-danger"
                                    onclick="return confirm('Удалить категорию «${c.name}»?')">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="3" class="text-center text-muted">Категорий нет</td>
            </tr>
        </#if>
        </tbody>
    </table>

</@main.layout>
