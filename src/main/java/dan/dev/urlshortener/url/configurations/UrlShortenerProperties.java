package dan.dev.urlshortener.url.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url-shortener.config")
public record UrlShortenerProperties(
        ShortenAlgorithm algorithm
) {
    public enum ShortenAlgorithm {
        BASE_62,
        TIME_BASED

    }
}