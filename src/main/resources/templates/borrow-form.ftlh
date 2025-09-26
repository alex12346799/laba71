<#import "spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Заявка на книгу</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<#assign _auth      = (SPRING_SECURITY_CONTEXT.authentication)!>
<#assign _principal = (_auth.principal)!>
<#assign _display   = (_principal.name)!((_principal.username)!'Читатель')>
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow p-4" style="max-width: 420px; width: 100%;">
        <h4 class="mb-3 text-center">${bookDto.title!''}</h4>
        <p class="text-muted text-center mb-4">Автор: ${bookDto.author!''}</p>

        <#if error??>
            <div class="alert alert-danger text-center mb-3">
                ${error}
            </div>
        </#if>

        <form action="/books/${bookDto.id}/borrow" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}" />
            </#if>

            <div class="text-center mb-3">
                <label class="form-label d-block"><strong>Номер читательского билета</strong></label>
                <#if _display??>
                    <p class="form-control-plaintext fs-5 fw-bold">${_display}</p>
                    <input type="hidden" name="libraryCardNumber" value="${_display}">
                <#else>
                    <input type="text" class="form-control" name="libraryCardNumber" required>
                </#if>
            </div>

            <div class="mb-4">
                <label for="returnDate" class="form-label">Дата возврата</label>
                <input type="date" class="form-control" id="returnDate" name="dueDate" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">Отправить заявку</button>
        </form>
    </div>
</div>
</body>
</html>