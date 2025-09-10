package dan.dev.urlshortener.url.services;

import dan.dev.urlshortener.url.events.ShortenUrlAccessedEvent;
import dan.dev.urlshortener.url.exceptions.ShortenUrlNotFoundException;
import dan.dev.urlshortener.url.models.ShortenUrl;
import dan.dev.urlshortener.url.repositories.ShortenUrlRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class ShortenUrlService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ShortenUrlRepository shortenUrlRepository;
    // TODO("refactor to interface")
    private final Base62UrlShortener urlShortener;

    public ShortenUrlService(ApplicationEventPublisher applicationEventPublisher,
                             ShortenUrlRepository shortenUrlRepository,
                             Base62UrlShortener urlShortener) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.shortenUrlRepository = shortenUrlRepository;
        this.urlShortener = urlShortener;
    }

    public ShortenUrl shortUrl(String url) {
        String code = urlShortener.shortUrl(url);
        return shortenUrlRepository.findFirstByCode(code).orElseGet(() -> {
            ShortenUrl shortenUrl = new ShortenUrl.ShortenUrlBuilder().code(code).url(url).createdAt(now()).build();
            return shortenUrlRepository.save(shortenUrl);
        });
    }

    public ShortenUrl accessUrl(String code) {
        var shortenUrl = shortenUrlRepository.findFirstByCode(code)
                .orElseThrow(() -> new ShortenUrlNotFoundException("url not found"));
        applicationEventPublisher.publishEvent(new ShortenUrlAccessedEvent(shortenUrl.getCode()));
        return shortenUrl;
    }

}
