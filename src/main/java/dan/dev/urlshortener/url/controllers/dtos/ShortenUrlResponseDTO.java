package dan.dev.urlshortener.url.controllers.dtos;

public record ShortenUrlResponseDTO(
        String url,
        String code
) {
}
