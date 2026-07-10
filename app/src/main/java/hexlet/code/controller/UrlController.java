package hexlet.code.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import org.jsoup.Jsoup;
import java.net.URI;
import java.time.Instant;
import java.util.Map;

public class UrlController {

    public static void index(Context ctx) {
        ctx.render("index.jte", Map.of(
            "flash", "",
            "flashType", ""
        ));
    }

    public static void create(Context ctx) throws Exception {
        String inputUrl = ctx.formParam("url");
        URI parsedUrl;

        try {
            parsedUrl = new URI(inputUrl);
            parsedUrl.toURL();
        } catch (Exception e) {
            ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
            ctx.render("index.jte", Map.of(
                "flash", "Некорректный URL",
                "flashType", "danger"
            ));
            return;
        }

        String protocol = parsedUrl.getScheme();
        String authority = parsedUrl.getAuthority();
        String normalizedUrl = protocol + "://" + authority;

        var existingUrl = UrlRepository.findByName(normalizedUrl);
        if (existingUrl.isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "info");
            ctx.redirect("/urls/" + existingUrl.get().getId());
        } else {
            var newUrl = new Url(normalizedUrl, Instant.now());
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashType", "success");
            ctx.redirect("/urls/" + newUrl.getId());
        }
    }

    public static void listUrls(Context ctx) throws Exception {
        var urls = UrlRepository.getEntities();
        var latestChecks = UrlCheckRepository.getLatestChecks();

        ctx.render("urls/index.jte", Map.of(
            "urls", urls,
            "latestChecks", latestChecks
        ));
    }

    public static void showUrl(Context ctx) throws Exception {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id);

        if (url.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND);
            return;
        }

        var checks = UrlCheckRepository.findByUrlId(id);

        String flash = ctx.sessionAttribute("flash");
        String flashType = ctx.sessionAttribute("flashType");

        ctx.sessionAttribute("flash", null);
        ctx.sessionAttribute("flashType", null);

        ctx.render("urls/show.jte", Map.of(
            "url", url.get(),
            "checks", checks,
            "flash", flash == null ? "" : flash,
            "flashType", flashType == null ? "" : flashType
        ));
    }

    public static void checkUrl(Context ctx) throws Exception {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        var urlOptional = UrlRepository.find(id);

        if (urlOptional.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND);
            return;
        }
        var url = urlOptional.get();

        try {
            var response = kong.unirest.Unirest.get(url.getName()).asString();
            int statusCode = response.getStatus();

            if (statusCode >= 400) {
                ctx.sessionAttribute("flash", "Произошла ошибка при проверке");
                ctx.sessionAttribute("flashType", "danger");
                ctx.redirect("/urls/" + id);
                return;
            }

            var doc = Jsoup.parse(response.getBody());
            String title = doc.title();

            var h1Element = doc.selectFirst("h1");
            String h1 = h1Element != null ? h1Element.text() : "";

            var descriptionElement = doc.selectFirst("meta[name=description]");
            String description = descriptionElement != null ? descriptionElement.attr("content") : "";

            var check = new UrlCheck(
                id,
                statusCode,
                truncate(h1),
                truncate(title),
                truncate(description),
                Instant.now()
            );
            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке");
            ctx.sessionAttribute("flashType", "danger");
        }

        ctx.redirect("/urls/" + id);
    }

    private static String truncate(String text) {
        if (text == null) {
            return "";
        }
        return text.length() > 200 ? text.substring(0, 200) + "..." : text;
    }
}