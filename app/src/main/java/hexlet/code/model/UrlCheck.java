package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class UrlCheck {
    private Long id;
    private Long urlId;
    private int statusCode;
    private String h1;
    private String title;
    private String description;
    private Instant createdAt;

    public UrlCheck(Long urlId, int statusCode, String h1, String title, String description, Instant createdAt) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public UrlCheck(Long id, Long urlId, int statusCode, String h1, String title, String description, Instant createdAt) {
        this.id = id;
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }
}