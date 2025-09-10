package dan.dev.urlshortener.url.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "shorten_urls")
public class ShortenUrl {

    @Id
    private String id;
    @Indexed(unique = true)
    private final String code;
    private final String url;
    private final LocalDateTime createdAt;

    private ShortenUrl(String id, String code, String url, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.url = url;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static class ShortenUrlBuilder {

        private String id;
        private String code;
        private String url;
        private LocalDateTime createdAt;

        public ShortenUrlBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ShortenUrlBuilder url(String url) {
            this.url = url;
            return this;
        }

        public ShortenUrlBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShortenUrl build() {
            return new ShortenUrl(
                    null, code, url, createdAt
            );
        }

    }
}