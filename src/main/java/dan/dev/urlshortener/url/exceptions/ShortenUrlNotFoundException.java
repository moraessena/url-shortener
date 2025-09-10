package dan.dev.urlshortener.url.exceptions;

public class ShortenUrlNotFoundException extends RuntimeException {
    public ShortenUrlNotFoundException(String message) {
        super(message);
    }
}
