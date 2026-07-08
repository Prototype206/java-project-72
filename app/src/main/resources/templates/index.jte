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
                
                <form action="/urls" method="post" class="rss-form text-left">
                    <div class="row">
                        <div class="col">
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="url-input" name="url" placeholder="Ссылка" aria-label="url" autocomplete="off" required>
                                <label for="url-input">Ссылка</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-lg btn-primary px-sm-5 h-100">Проверить</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    `
)