<#--<#import "/spring.ftl" as spring>-->


<#--    <style>-->
<#--        .auth-bg {-->
<#--            min-height: 100vh;-->
<#--            display: grid;-->
<#--            place-items: center;-->
<#--            background: linear-gradient(135deg, #6366f1, #22c55e);-->
<#--        }-->

<#--        .auth-card {-->
<#--            width: 100%;-->
<#--            max-width: 460px;-->
<#--            background: #fff;-->
<#--            border-radius: 1rem;-->
<#--            box-shadow: 0 10px 30px rgba(0, 0, 0, .2);-->
<#--            padding: 2rem;-->
<#--        }-->

<#--        .auth-card h2 {-->
<#--            text-align: center;-->
<#--            margin-bottom: 1.5rem;-->
<#--        }-->
<#--    </style>-->

<#--    <div class="auth-bg">-->
<#--        <div class="auth-card">-->
<#--            <h2>Регистрация</h2>-->
<#--            <form action="/auth/register" method="post">-->
<#--                <#if _csrf??>-->
<#--                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->
<#--                </#if>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Имя</label>-->
<#--                    <@spring.formInput "registrationDto.name" 'class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Фамилия</label>-->
<#--                    <@spring.formInput "registrationDto.surname" 'class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Отчество</label>-->
<#--                    <@spring.formInput "registrationDto.patronymic" 'class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Адрес</label>-->
<#--                    <@spring.formInput "registrationDto.address" 'class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Номер паспорта</label>-->
<#--                    <@spring.formInput "registrationDto.passportNumber" 'class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Пароль</label>-->
<#--                    <@spring.formInput "registrationDto.password" 'type="password" class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <div class="form-outline mb-4">-->
<#--                    <label class="form-label">Подтвердите пароль</label>-->
<#--                    <@spring.formInput "registrationDto.confirmPassword" 'type="password" class="form-control form-control-lg"' />-->
<#--                    <@spring.showErrors "<br>" "error text-danger" />-->
<#--                </div>-->

<#--                <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>-->
<#--            </form>-->

<#--            <div class="text-center mt-3">-->
<#--                Уже есть аккаунт? <a href="/auth/login">Войти</a>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация | Библиотека</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Arial, sans-serif;
        }
        .auth-bg {
            min-height: 100vh;
            display: grid;
            place-items: center;
            background: linear-gradient(135deg, #6366f1, #22c55e);
        }
        .auth-card {
            width: 100%;
            max-width: 460px;
            background: #fff;
            border-radius: 1rem;
            box-shadow: 0 10px 30px rgba(0, 0, 0, .2);
            padding: 2rem 2.5rem;
            animation: fadeIn 0.7s;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(30px);}
            to { opacity: 1; transform: translateY(0);}
        }
        .auth-card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #6366f1;
            font-weight: 700;
            letter-spacing: 1px;
        }
        .form-outline {
            margin-bottom: 1.2rem;
        }
        .form-label {
            display: block;
            margin-bottom: .5rem;
            font-weight: 500;
            color: #374151;
        }
        .form-control {
            width: 100%;
            padding: .75rem 1rem;
            border: 1px solid #d1d5db;
            border-radius: .5rem;
            font-size: 1rem;
            transition: border-color .2s;
            background: #f9fafb;
        }
        .form-control:focus {
            border-color: #6366f1;
            outline: none;
            background: #fff;
        }
        .btn-primary {
            background: linear-gradient(90deg, #6366f1 60%, #22c55e 100%);
            color: #fff;
            border: none;
            padding: .85rem 0;
            border-radius: .5rem;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: box-shadow .2s;
            box-shadow: 0 2px 8px rgba(99,102,241,.15);
        }
        .btn-primary:hover {
            box-shadow: 0 4px 16px rgba(99,102,241,.18);
        }
        .text-danger {
            color: #ef4444;
            font-size: .95rem;
        }
        .text-center {
            text-align: center;
        }
        .mt-3 {
            margin-top: 1.5rem;
        }
        a {
            color: #6366f1;
            text-decoration: none;
            font-weight: 500;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="auth-bg">
    <div class="auth-card">
        <h2>Регистрация в библиотеке</h2>
        <form action="/auth/register" method="post">
            <#if _csrf??>
                <input
                        type="hidden"
                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                        value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <div class="form-outline">
                <label class="form-label" for="name">Имя</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>

            <div class="form-outline">
                <label class="form-label" for="surname">Фамилия</label>
                <input type="text" id="surname" name="surname" class="form-control" required>
            </div>

            <div class="form-outline">
                <label class="form-label" for="patronymic">Отчество</label>
                <input type="text" id="patronymic" name="patronymic" class="form-control">
            </div>

            <div class="form-outline">
                <label class="form-label" for="address">Адрес</label>
                <input type="text" id="address" name="address" class="form-control" required>
            </div>

            <div class="form-outline">
                <label class="form-label" for="passportNumber">Номер паспорта</label>
                <input type="text" id="passportNumber" name="passportNumber" class="form-control" required>
            </div>

            <div class="form-outline">
                <label class="form-label" for="password">Пароль</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="form-outline">
                <label class="form-label" for="confirmPassword">Подтвердите пароль</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>
        </form>
        <div class="text-center mt-3">
            Уже есть аккаунт? <a href="/auth/login">Войти</a>
        </div>
    </div>
</div>
</body>
</html>