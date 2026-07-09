package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;

import java.sql.Timestamp;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public final class AppTest {

    private Javalin app;
    private static MockWebServer mockServer;

    @BeforeAll
    public static void beforeAll() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        mockServer.shutdown();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Создаем свежий инстанс приложения для каждого теста
        app = App.getApp();

        // Очищаем таблицы перед тестом
        try (var conn = BaseRepository.dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM url_checks");
            stmt.execute("DELETE FROM urls");
        }
    }

    @AfterEach
    public void tearDown() {
        // ОБЯЗАТЕЛЬНО останавливаем приложение, чтобы освободить порт и H2 базу
        if (app != null) {
            app.stop();
        }
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://github.com";
            Response response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://github.com");

            var actualUrl = UrlRepository.findByName("https://github.com");
            assertThat(actualUrl).isPresent();
        });
    }

    @Test
    public void testCreateDuplicateUrl() throws Exception {
        var url = new Url("https://hexlet.io", new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://hexlet.io";
            Response response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            String body = response.body().string();
            assertThat(body).contains("https://hexlet.io");
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=not-a-valid-url";
            Response response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(422);
        });
    }

    @Test
    public void testUrlPage() throws Exception {
        var url = new Url("https://google.com", new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://google.com");
        });
    }

    @Test
    public void testUrlsListPage() throws Exception {
        var url = new Url("https://yandex.ru", new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://yandex.ru");
        });
    }

    @Test
    public void testShowNonExistingUrl() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls/9999909");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCreateInvalidUrlRender() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=not-a-valid-url";
            Response response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(422);
            String body = response.body().string();
            assertThat(body).contains("Анализатор страниц");
            assertThat(body).contains("Некорректный URL");
        });
    }

    @Test
    public void testShowUrlInvalidIdFormat() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls/abc");
            assertThat(response.code()).isEqualTo(500);
        });
    }

    @Test
    public void testUrlCheckWithMock() throws Exception {
        String fakeHtml = "<html><head><title>Test Title</title></head>"
                + "<body><h1>Test H1</h1><meta name=\"description\" content=\"Test Desc\" /></body></html>";

        mockServer.enqueue(new MockResponse().setBody(fakeHtml).setResponseCode(200));

        String mockUrl = mockServer.url("/").toString();

        var url = new Url(mockUrl, new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);

            var checks = UrlCheckRepository.findByUrlId(url.getId());
            assertThat(checks).isNotEmpty();

            var lastCheck = checks.get(0);
            assertThat(lastCheck.getStatusCode()).isEqualTo(200);
            assertThat(lastCheck.getTitle()).isEqualTo("Test Title");
            assertThat(lastCheck.getH1()).isEqualTo("Test H1");
            assertThat(lastCheck.getDescription()).isEqualTo("Test Desc");
        });
    }
}