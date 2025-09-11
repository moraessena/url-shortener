package dan.dev.urlshortener.url.services.algorithm;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "url-shortener.config", name = "algorithm", havingValue = "TIME_BASED")
class TimeBasedUrlShortener implements ShortenService {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String shorten(String url) {
        long timestamp = System.currentTimeMillis();

        // Mix with URL hash for better distribution
        long urlHash = Math.abs(url.hashCode()) & 0xFFFF; // 16 bits
        long combined = (timestamp << 16) ^ urlHash;

        return encodeToBase62(combined);
    }

    private String encodeToBase62(long value) {
        if (value == 0) return String.valueOf(BASE62.charAt(0));

        StringBuilder encoded = new StringBuilder();
        while (value > 0) {
            encoded.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return encoded.reverse().toString();
    }

}
