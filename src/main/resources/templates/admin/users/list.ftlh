<#import "../../layout.ftlh" as main>

<@main.layout title="Админ — Пользователи">

    <h3>Пользователи</h3>

    <!-- Форма добавления администратора -->
    <div class="card mb-4">
        <div class="card-header">Создать администратора</div>
        <div class="card-body">
            <form method="post" action="/admin/users/admins" class="row g-2">
                <#if _csrf??>
                    <input
                            type="hidden"
                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                            value="${(_csrf.token)!'csrf-token'}"/>
                </#if>

                <div class="col-sm-3">
                    <input type="text" name="name" class="form-control" placeholder="Имя" required>
                </div>
                <div class="col-sm-3">
                    <input type="text" name="passportNumber" class="form-control" placeholder="Паспорт" required>
                </div>
                <div class="col-sm-3">
                    <input type="password" name="password" class="form-control" placeholder="Пароль" required>
                </div>
                <div class="col-sm-3">
                    <button class="btn btn-success w-100" type="submit">Создать</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Таблица пользователей -->
    <table class="table table-striped table-hover align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Библиотечный номер</th>
            <th>Роль</th>
            <th>Активен</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#if items?? && (items?size > 0)>
            <#list items as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td>${u.surname!""}</td>
                    <td><code>${u.libraryCardNumber}</code></td>
                    <td>
                        <#if u.roleName == "ROLE_ADMIN">
                            <span class="badge bg-danger">Админ</span>
                        <#else>
                            <span class="badge bg-secondary">Читатель</span>
                        </#if>
                    </td>
                    <td>
                        <#if u.enabled>
                            <span class="badge bg-success">Да</span>
                        <#else>
                            <span class="badge bg-danger">Нет</span>
                        </#if>
                    </td>
                    <td>
                        <!-- Удаление -->
                        <form method="post" action="/admin/users/${u.id}/delete" class="d-inline">
                            <#if _csrf??>
                                <input
                                        type="hidden"
                                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                                        value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <button class="btn btn-sm btn-outline-danger"
                                    onclick="return confirm('Удалить пользователя ${u.name}?')">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="7" class="text-center text-muted">Нет пользователей</td>
            </tr>
        </#if>
        </tbody>
    </table>

</@main.layout>
