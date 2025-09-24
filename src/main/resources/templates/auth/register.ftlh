<#import "/spring.ftl" as spring>


    <style>
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
            padding: 2rem;
        }

        .auth-card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
        }
    </style>

    <div class="auth-bg">
        <div class="auth-card">
            <h2>Регистрация</h2>
            <form action="/auth/register" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </#if>

                <div class="form-outline mb-4">
                    <label class="form-label">Имя</label>
                    <@spring.formInput "registrationDto.name" 'class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label">Фамилия</label>
                    <@spring.formInput "registrationDto.surname" 'class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label">Отчество</label>
                    <@spring.formInput "registrationDto.patronymic" 'class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label">Адрес</label>
                    <@spring.formInput "registrationDto.address" 'class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label">Номер паспорта</label>
                    <@spring.formInput "registrationDto.passportNumber" 'class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label">Пароль</label>
                    <@spring.formInput "registrationDto.password" 'type="password" class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label">Подтвердите пароль</label>
                    <@spring.formInput "registrationDto.confirmPassword" 'type="password" class="form-control form-control-lg"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>
            </form>

            <div class="text-center mt-3">
                Уже есть аккаунт? <a href="/auth/login">Войти</a>
            </div>
        </div>
    </div>


