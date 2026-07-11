@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import java.util.List
@import java.util.Map
@import java.time.format.DateTimeFormatter
@import java.time.ZoneId

@param List<Url> urls
@param Map<Long, UrlCheck> latestChecks

@template.layout.page(
    content = @`
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3" data-test="urls">
                <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th>Имя</th>
                        <th class="col-2">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                </thead>
                <tbody>
                    @for(Url url : urls)
                        <tr>
                            <td>${url.getId()}</td>
                            <td>
                                <a href="/urls/${url.getId()}">${url.getName()}</a>
                            </td>
                            <td>
                                @if(latestChecks != null && latestChecks.containsKey(url.getId()) && latestChecks.get(url.getId()) != null)
                                    ${latestChecks.get(url.getId()).getCreatedAt().atZone(java.time.ZoneId.of("UTC")).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}
                                @endif
                            </td>
                            <td>
                                @if(latestChecks != null && latestChecks.containsKey(url.getId()) && latestChecks.get(url.getId()) != null)
                                    ${latestChecks.get(url.getId()).getStatusCode()}
                                @endif
                            </td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    `
)