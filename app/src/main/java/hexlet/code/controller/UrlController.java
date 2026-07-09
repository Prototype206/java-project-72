package hexlet.code.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import org.jsoup.Jsoup;
import java.net.URI;
import java.sql.Timestamp;

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
            ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
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

            java.util.Map<Long, UrlCheck> latestChecks = new java.util.HashMap<>();
            for (var url : urls) {
                var checks = UrlCheckRepository.findByUrlId(url.getId());
                if (!checks.isEmpty()) {
                    latestChecks.put(url.getId(), checks.get(0));
                }
            }

            ctx.render("urls/index.jte", java.util.Map.of(
                "urls", urls,
                "latestChecks", latestChecks
            ));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
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

            var checks = UrlCheckRepository.findByUrlId(id);

            String flash = ctx.sessionAttribute("flash");
            String flashType = ctx.sessionAttribute("flashType");

            ctx.sessionAttribute("flash", null);
            ctx.sessionAttribute("flashType", null);

            ctx.render("urls/show.jte", java.util.Map.of(
                "url", url.get(),
                "checks", checks,
                "flash", flash == null ? "" : flash,
                "flashType", flashType == null ? "" : flashType
            ));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.result(e.getMessage());
        }
    }

    public static void checkUrl(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();

        try {
            var urlOptional = UrlRepository.find(id);

            if (urlOptional.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND);
                return;
            }
            var url = urlOptional.get();

            var response = Jsoup.connect(url.getName())
                .ignoreHttpErrors(true)
                .execute();

            int statusCode = response.statusCode();
            var doc = response.parse();

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
                new Timestamp(System.currentTimeMillis())
            );
            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");
        } catch (Exception e) {
            ctx.status(500);
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            ctx.result("DEBUG ERROR: " + e.getMessage() + "\n" + sw.toString());
            return;
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