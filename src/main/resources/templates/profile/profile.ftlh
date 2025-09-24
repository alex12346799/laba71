<#--<#import "../index.ftlh" as main>-->

<section class="container my-5">
    <div class="row">
        <div class="col-md-12">
            <h2>Профиль пользователя</h2>
            <p><strong>ФИО:</strong> ${profileDto.readerName}</p>
            <p><strong>Читательский билет:</strong> ${profileDto.libraryCardNumber}</p>
        </div>
    </div>

    <div class="row my-4">
        <div class="col-md-12">
            <h4>Фильтр по дате</h4>
            <form action="/auth/profile" method="get" class="row g-3">
                <div class="col-auto">
                    <label for="startDate" class="form-label">С</label>
                    <input type="date" id="startDate" name="startDate" class="form-control"
                           value="${profileDto.fromDate!""}">
                </div>
                <div class="col-auto">
                    <label for="endDate" class="form-label">По</label>
                    <input type="date" id="endDate" name="endDate" class="form-control"
                           value="${profileDto.toDate!""}">
                </div>
                <div class="col-auto align-self-end">
                    <button type="submit" class="btn btn-primary mb-3">Применить</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h4>История взятых книг</h4>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Название книги</th>
                    <th>Автор</th>
                    <th>Дата выдачи</th>
                    <th>Ожидаемая/Фактическая дата возврата</th>
                    <th>Статус</th>
                </tr>
                </thead>
                <tbody>
                <#-- Перебираем займы через profileDto.loans, null-safe -->
                <#list profileDto.loans![] as loan>
                    <tr>
                        <td>${loan.title!""}</td>
                        <td>${loan.author!""}</td>
                        <td>${loan.borrowDate!""}</td>
                        <td>
                            <#if loan.returnedAt??>
                                ${loan.returnedAt}
                            <#else>
                                ${loan.dueDate!""}
                            </#if>
                        </td>

                        <td>
                            <#if loan.returnedAt??>
                                <span class="badge bg-success">Возвращено</span>
                            <#elseif loan.dueDate?? && loan.dueDate < .now>
                                <span class="badge bg-danger">Просрочено</span>
                            <#else>
                                <span class="badge bg-warning text-dark">Ожидается</span>
                            </#if>
                        </td>
                    </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>
</section>
