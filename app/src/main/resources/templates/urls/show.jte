@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import java.time.format.DateTimeFormatter
@import java.time.ZoneId

@param Url url
@param java.util.List<UrlCheck> checks
@param String flash
@param String flashType

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц - Детали сайта</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Анализатор страниц</a>
            <div class="collapse navbar-collapse">
                <div class="navbar-nav">
                    <a class="nav-link" href="/">Главная</a>
                    <a class="nav-link" href="/urls">Сайты</a>
                </div>
            </div>
        </div>
    </nav>

    @if(flash != null && !flash.isEmpty())
        <div class="alert alert-${flashType} alert-dismissible fade show rounded-0 m-0" role="alert">
            ${flash}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif

    <main class="flex-grow-1 container-lg mt-5">
        <h1>Сайт: ${url.getName()}</h1>

        <div class="table-responsive mt-3">
            <table class="table table-bordered table-hover">
                <tbody>
                    <tr>
                        <th class="col-2">ID</th>
                        <td>${url.getId()}</td>
                    </tr>
                    <tr>
                        <th>Имя</th>
                        <td>${url.getName()}</td>
                    </tr>
                    <tr>
                        <th>Дата создания</th>
                        <td>
                            @if(url.getCreatedAt() != null)
                                ${url.getCreatedAt().atZone(java.time.ZoneId.of("UTC")).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))}
                            @endif
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

		<h2 class="mt-5">Проверки</h2>
        
        <form method="post" action="/urls/${url.getId()}/checks" class="mb-3">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>

        <div class="table-responsive">
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
                    @if(checks != null && !checks.isEmpty())
                        @for(var check : checks)
                            <tr>
                                <td>${check.getId()}</td>
                                <td>${check.getStatusCode()}</td>
                                <td>${check.getH1()}</td>
                                <td>${check.getTitle()}</td>
                                <td>${check.getDescription()}</td>
                                <td>${check.getCreatedAt().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}</td>
                            </tr>
                        @endfor
                    @endif
                </tbody>
            </table>
        </div>
    </main>

    <footer class="footer border-top py-3 bg-white mt-5">
        <div class="container-xl">
            <div class="text-center text-muted">
                <a href="https://hexlet.io" target="_blank">Hexlet</a>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>