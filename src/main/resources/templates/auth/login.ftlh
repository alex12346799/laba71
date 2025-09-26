<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Вход | Библиотека</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #6366f1, #22c55e);
            min-height: 100vh;
            display: grid;
            place-items: center;
        }

        .login-card {
            display: flex;
            max-width: 800px;
            background: #fff;
            border-radius: 1rem;
            box-shadow: 0 10px 30px rgba(0, 0, 0, .18);
            overflow: hidden;
            animation: fadeIn 0.7s;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(30px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-img {
            width: 320px;
            object-fit: cover;
            display: block;
        }

        .login-form-section {
            flex: 1;
            padding: 2.5rem 2rem;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-form-section h2 {
            text-align: center;
            color: #6366f1;
            font-weight: 700;
            margin-bottom: 2rem;
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
            box-shadow: 0 2px 8px rgba(99, 102, 241, .15);
            width: 100%;
        }

        .btn-primary:hover {
            box-shadow: 0 4px 16px rgba(99, 102, 241, .18);
        }

        .btn-outline-primary {
            border: 1px solid #6366f1;
            color: #6366f1;
            background: #fff;
            padding: .5rem 1.2rem;
            border-radius: .5rem;
            font-size: .95rem;
            font-weight: 500;
            cursor: pointer;
            transition: background .2s, color .2s;
        }

        .btn-outline-primary:hover {
            background: #6366f1;
            color: #fff;
        }

        .text-center {
            text-align: center;
        }

        .mt-3 {
            margin-top: 1.5rem;
        }

        @media (max-width: 700px) {
            .login-card {
                flex-direction: column;
                max-width: 95vw;
            }

            .login-img {
                width: 100%;
                height: 180px;
            }
        }
    </style>
</head>

<body>
<div class="login-card">
    <img src="https://mdbootstrap.com/img/new/ecommerce/vertical/004.jpg" alt="Библиотека" class="login-img">
    <div class="login-form-section">
        <h2>Вход в библиотеку</h2>
        <form action="/login" method="post">
            <#if _csrf??>
                <input
                        type="hidden"
                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                        value="${(_csrf.token)!'csrf-token'}" />
            </#if>
            <div class="form-outline">
                <label class="form-label" for="libraryCardNumber">Номер читательского билета</label>
                <input type="text" id="libraryCardNumber" name="libraryCardNumber" class="form-control" required>
            </div>

            <div class="form-outline">
                <label class="form-label" for="password">Пароль</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">Войти</button>

            <div class="text-center mt-3"></div>
            <a href="/auth/register" class="btn-outline-primary">Зарегистрироваться</a>
        </form>

    </div>
</div>
</body>

</html>