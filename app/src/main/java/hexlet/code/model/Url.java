package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class Url {
    private Long id;
    private String name;
    private Instant createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url(String name, Instant createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public Url(Long id, String name, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
}