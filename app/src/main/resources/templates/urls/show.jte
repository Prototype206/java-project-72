@import hexlet.code.model.Url
@import java.time.format.DateTimeFormatter

@param Url url
@param String flash = null
@param String flashType = null

@template.layout.page(
    flash = flash,
    flashType = flashType,
    content = @`
        <div class="container-lg mt-5">
            <h1>Сайт: ${url.getName()}</h1>
            
            <table class="table table-bordered table-hover mt-3" data-test="url">
                <tbody>
                    <tr>
                        <td class="col-2">ID</td>
                        <td>${url.getId()}</td>
                    </tr>
                    <tr>
                        <td>Имя</td>
                        <td>${url.getName()}</td>
                    </tr>
                    <tr>
                        <td>Дата создания</td>
                        <td>${url.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}</td>
                    </tr>
                </tbody>
            </table>

            <h2 class="mt-5">Проверки</h2>
            
            <form method="post" action="/urls/${url.getId()}/checks">
                <input type="submit" class="btn btn-primary mb-3" value="Запустить проверку">
            </form>

            <table class="table table-bordered table-hover" data-test="checks">
                <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th class="col-1">Код ответа</th>
                        <th>h1</th>
                        <th>title</th>
                        <th>description</th>
                        <th class="col-2">Дата создания</th>
                    </tr>
                </thead>
                <tbody>
                    </tbody>
            </table>
        </div>
    `
)