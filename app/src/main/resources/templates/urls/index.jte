@import hexlet.code.model.Url
@import java.util.List
@param List<Url> urls

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
                            <td></td>
                            <td></td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    `
)