
<#--&lt;#&ndash;    <div class="card">&ndash;&gt;-->
<#--&lt;#&ndash;        <div class="card-body">&ndash;&gt;-->
<#--&lt;#&ndash;            <h5>${bookDto.title}</h5>&ndash;&gt;-->
<#--&lt;#&ndash;            <p>Автор: ${bookDto.author}</p>&ndash;&gt;-->

<#--&lt;#&ndash;            <#if bookDto.expectedAvailableAt??>&ndash;&gt;-->
<#--&lt;#&ndash;                <p>Дата возврата: ${bookDto.expectedAvailableAt}</p>&ndash;&gt;-->
<#--&lt;#&ndash;            <#else>&ndash;&gt;-->
<#--&lt;#&ndash;                <form action="/books/${bookDto.id}/borrow-form" method="get">&ndash;&gt;-->
<#--&lt;#&ndash;                    <button type="submit">Получить</button>&ndash;&gt;-->
<#--&lt;#&ndash;                </form>&ndash;&gt;-->
<#--&lt;#&ndash;            </#if>&ndash;&gt;-->
<#--&lt;#&ndash;        </div>&ndash;&gt;-->
<#--&lt;#&ndash;    </div>&ndash;&gt;-->

<#--<div class="card mb-3 shadow-sm" style="max-width: 18rem;">-->

<#--    <div class="card-body d-flex flex-column">-->
<#--        <h5 class="card-title">${bookDto.title}</h5>-->
<#--        <p class="card-text text-muted mb-2">Автор: ${bookDto.author}</p>-->

<#--        <#if bookDto.expectedAvailableAt??>-->
<#--            <p class="text-warning mb-3">-->
<#--                📅 Дата возврата: ${bookDto.expectedAvailableAt}-->
<#--            </p>-->
<#--            <button class="btn btn-secondary w-100" disabled>Занята</button>-->
<#--        <#else>-->
<#--            <form action="/books/${bookDto.id}/borrow-form" method="get" class="mt-auto">-->
<#--                <button type="submit" class="btn btn-primary w-100">-->
<#--                    Получить-->
<#--                </button>-->
<#--            </form>-->
<#--        </#if>-->
<#--    </div>-->
<#--</div>-->


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Оформление книги</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow p-4" style="max-width: 400px; width: 100%;">
        <h4 class="mb-3 text-center">${bookDto.title}</h4>
        <p class="text-muted text-center mb-4">Автор: ${bookDto.author}</p>

        <#if bookDto.expectedAvailableAt??>
            <div class="alert alert-warning text-center mb-3">
                📅 Дата возврата: ${bookDto.expectedAvailableAt}
            </div>
        <#else>


                <div class="mb-3">
                    <label for="readerCard" class="form-label">Читательский билет</label>
                    <!-- Подставляем номер читательского билета и делаем поле readonly -->
                    <input type="text" class="form-control" id="readerCard" name="libraryCardNumber"
                           value="${libraryCardNumber}" readonly>
                </div>
                <div class="mb-4">
                    <label for="returnDate" class="form-label">Дата возврата</label>
                    <input type="date" class="form-control" id="returnDate" name="returnDate" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Получить</button>

        </#if>
    </div>
</div>
</body>
</html>