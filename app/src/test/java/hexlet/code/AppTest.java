package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;
import java.sql.Timestamp;

public final class AppTest {

    private Javalin app;

    @BeforeEach
    public void setUp() throws Exception {
        app = App.getApp();
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
}