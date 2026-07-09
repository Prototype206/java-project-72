@param String flash = null
@param String flashType = null

@template.layout.page(
    flash = flash,
    flashType = flashType,
    content = @`
        <div class="row">
            <div class="col-md-10 col-lg-8 mx-auto text-white">
                <h1 class="display-3 mb-0 text-dark">Анализатор страниц</h1>
                <p class="lead text-muted">Бесплатная проверка сайтов на SEO пригодность</p>
                
                <form action="/urls" method="post" class="row">
                    <div class="col-8">
                        <label for="url-name" class="visually-hidden">Url для проверки</label>
                        <input
                            id="url-name"
                            type="text"
                            name="url"
                            class="form-control form-control-lg"
                            placeholder="https://www.example.com"
                            autocomplete="off"
                            required
                        >
                    </div>
                    <div class="col-2">
                        <input type="submit" class="btn btn-primary btn-lg ms-3 px-5 text-uppercase mx-3" value="Проверить">
                    </div>
                </form>
            </div>
        </div>
    `
)