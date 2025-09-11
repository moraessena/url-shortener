package dan.dev.urlshortener.url.configurations;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {UrlShortenerProperties.class})
public class UrlShortenerPropertiesConfig {
}
