<#import "layout.ftlh" as main>
<@main.layout title="Ошибка">

    <div class="container my-5" style="max-width:760px;">
        <div class="card shadow-sm">
            <div class="card-body">
                <h3 class="card-title mb-3">
                    Ошибка <span class="badge bg-danger">${(err.status)!'—'}</span>
                </h3>
                <p class="mb-1"><strong>Причина:</strong> ${(err.reason)!'Неизвестно'}</p>
                <p class="mb-1"><strong>Сообщение:</strong> ${(err.message)!'Без сообщения'}</p>
                <p class="mb-1"><strong>Путь:</strong> <code>${(err.path)!'—'}</code></p>
                <p class="text-muted small mb-3"><strong>Время:</strong> ${(err.timestamp)!}</p>

                <#if err.errors?? && (err.errors?size > 0)>
                    <hr>
                    <h6>Детали:</h6>
                    <ul class="mb-0">
                        <#list err.errors as e>
                            <li>
                                <#if e.field??><code>${e.field}</code>: </#if>
                                ${(e.error)!}
                            </li>
                        </#list>
                    </ul>
                </#if>

                <div class="mt-4 d-flex gap-2">
                    <a class="btn btn-primary" href="/">На главную</a>
                    <a class="btn btn-outline-secondary" href="javascript:history.back()">Назад</a>
                </div>
            </div>
        </div>
    </div>

</@main.layout>
