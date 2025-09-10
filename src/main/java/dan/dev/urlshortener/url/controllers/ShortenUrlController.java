package dan.dev.urlshortener.url.controllers;

import dan.dev.urlshortener.url.controllers.dtos.ShortenUrlRequestDTO;
import dan.dev.urlshortener.url.controllers.dtos.ShortenUrlResponseDTO;
import dan.dev.urlshortener.url.services.ShortenUrlService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/shorten")
public class ShortenUrlController {

    private final ShortenUrlService shortenUrlService;

    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ShortenUrlResponseDTO shortenUrl(@RequestBody ShortenUrlRequestDTO requestDTO) {
        var shortenUrl = shortenUrlService.shortUrl(requestDTO.url());
        return new ShortenUrlResponseDTO(shortenUrl.getUrl(), shortenUrl.getCode());
    }

}
