package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import hexlet.code.controller.UrlController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

public class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:analytics;DB_CLOSE_DELAY=-1;");
    }

    public static Javalin getApp() throws Exception {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());

        if (getDatabaseUrl().contains("h2")) {
            hikariConfig.setUsername("sa");
            hikariConfig.setPassword("");
        }

        var dataSource = new HikariDataSource(hikariConfig);

        var is = App.class.getClassLoader().getResourceAsStream("schema.sql");
        try (var reader = new BufferedReader(new InputStreamReader(is))) {
            var sql = reader.lines().collect(Collectors.joining("\n"));
            try (var conn = dataSource.getConnection();
                 var stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }

        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            var codeResolver = new ResourceCodeResolver("templates");
            var templateEngine = TemplateEngine.create(codeResolver, gg.jte.ContentType.Html);
            config.fileRenderer(new JavalinJte(templateEngine));
        });

        app.get("/", UrlController::index);
        app.post("/urls", UrlController::create);
        app.get("/urls", UrlController::listUrls);
        app.get("/urls/{id}", UrlController::showUrl);

        return app;
    }

    public static void main(String[] args) throws Exception {
        var app = getApp();
        app.start(getPort());
    }
}