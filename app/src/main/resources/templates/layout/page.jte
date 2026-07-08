@import gg.jte.Content

@param Content content
@param String flash = null
@param String flashType = null

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid container-xl">
            <a class="navbar-brand" href="/">Анализатор страниц</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/urls">Сайты</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

	@if(flash != null && !flash.isEmpty())
	    <div class="alert alert-${flashType} alert-dismissible fade show" role="alert">
	        ${flash}
	        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	    </div>
	@endif

    <main class="flex-grow-1 mt-5">
        <div class="container-xl">
            ${content}
        </div>
    </main>

    <footer class="footer border-top py-3 bg-light mt-auto">
        <div class="container-xl">
            <div class="text-center text-muted">
                built by <a href="https://ru.hexlet.io" target="_blank" class="text-decoration-none">Hexlet</a>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>