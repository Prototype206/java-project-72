package hexlet.code.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Collections;

public class UrlController {

    public static void index(Context ctx) {
        ctx.render("index.jte", java.util.Collections.emptyMap());
    }

    public static void create(Context ctx) {
        String inputUrl = ctx.formParam("url");
        String normalizedUrl;

        try {
            var uri = new URI(inputUrl);
            var url = uri.toURL();

            String protocol = url.getProtocol();
            String authority = url.getAuthority();
            normalizedUrl = protocol + "://" + authority;
        } catch (Exception e) {
            ctx.status(io.javalin.http.HttpStatus.UNPROCESSABLE_CONTENT);
            ctx.render("index.jte", java.util.Map.of(
                "flash", "Некорректный URL",
                "flashType", "danger"
            ));
            return;
        }

        try {
            var existingUrl = UrlRepository.findByName(normalizedUrl);
            if (existingUrl.isPresent()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType", "info");
                ctx.header("Cache-Control", "no-cache, no-store, must-revalidate");
                ctx.redirect("/urls/" + existingUrl.get().getId());
            } else {
                var newUrl = new Url(normalizedUrl, new Timestamp(System.currentTimeMillis()));
                UrlRepository.save(newUrl);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashType", "success");
                ctx.header("Cache-Control", "no-cache, no-store, must-revalidate");
                ctx.redirect("/urls/" + newUrl.getId());
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.result("Ошибка базы данных: " + e.getMessage());
        }
    }

    public static void listUrls(Context ctx) {
        try {
            var urls = UrlRepository.getEntities();
            ctx.render("urls/index.jte", Collections.singletonMap("urls", urls));
        } catch (Exception e) {
            ctx.status(500);
            ctx.result(e.getMessage());
        }
    }

    public static void showUrl(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            var url = UrlRepository.find(id);
            if (url.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND);
                return;
            }

            String flash = ctx.sessionAttribute("flash");
            String flashType = ctx.sessionAttribute("flashType");

            ctx.sessionAttribute("flash", null);
            ctx.sessionAttribute("flashType", null);

            ctx.render("urls/show.jte", java.util.Map.of(
                "url", url.get(),
                "flash", flash == null ? "" : flash,
                "flashType", flashType == null ? "" : flashType
            ));
        } catch (Exception e) {
            ctx.status(500);
            ctx.result(e.getMessage());
        }
    }
}