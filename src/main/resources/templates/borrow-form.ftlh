<#import "spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Оформление книги</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<#assign _auth         = (SPRING_SECURITY_CONTEXT.authentication)!>
<#assign _principal    = (_auth.principal)!>
<#assign _displayName  = (_principal.name)!((_principal.username)!'Пользователь')>
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow p-4" style="max-width: 400px; width: 100%;">
        <h4 class="mb-3 text-center">${bookDto.title!''}</h4>
        <p class="text-muted text-center mb-4">Автор: ${bookDto.author!''}</p>

        <#if error??>
            <div class="alert alert-danger text-center mb-3">
                ${error}
            </div>
        </#if>

        <form action="/books/${bookDto.id}/borrow" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
            </#if>

            <div class="text-center mb-3">
                <label class="form-label d-block"><strong>Читательский билет</strong></label>
                <#if _displayName??>
                    <p class="form-control-plaintext fs-5 fw-bold">${_displayName}</p>
                    <input type="hidden" name="libraryCardNumber" value="${_displayName}">
                <#else>
                    <p class="text-muted">Читательский билет недоступен</p>
                </#if>
            </div>

            <div class="mb-4">
                <label for="returnDate" class="form-label">Дата возврата</label>
                <input type="date" class="form-control" id="returnDate" name="dueDate" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">Подать заявку</button>
        </form>


    </div>
</div>

</body>
</html>
