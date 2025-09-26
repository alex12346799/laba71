<#import "../layout.ftlh" as main>

<@main.layout title="Административная панель">
    <div class="row g-3">
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/categories">Категории</a></div>
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/books">Книги</a></div>
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/users">Пользователи</a></div>
        <div class="col-md-3"><a class="btn btn-outline-primary w-100" href="/admin/loans">Выдачи</a></div>
    </div>
    <div class="row g-3 mt-3">
        <div class="col-md-3"><a class="btn btn-primary w-100" href="/admin/requests">Заявки читателей</a></div>
    </div>
</@main.layout>