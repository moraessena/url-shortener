package dan.dev.urlshortener.url.services;

import org.springframework.stereotype.Component;

@Component
class Base62UrlShortener {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = BASE62.length();

    public String shortUrl(String url) {
        long urlHash = generateHashFromUrl(url);
        return encodeToBase62(Math.abs((urlHash)));
    }

    private long generateHashFromUrl(String url) {
        return url.hashCode();
    }

    private String encodeToBase62(long id) {
        if (id == 0) return String.valueOf(BASE62.charAt(0));

        StringBuilder encoded = new StringBuilder();
        while (id > 0) {
            encoded.append(BASE62.charAt((int) (id % BASE)));
            id /= BASE;
        }
        return encoded.reverse().toString();
    }
}
