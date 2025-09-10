package dan.dev.urlshortener.url.services;

import dan.dev.urlshortener.url.models.ShortenUrl;
import dan.dev.urlshortener.url.repositories.ShortenUrlRepository;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class ShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;
    private final Base62UrlShortener urlShortener;

    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository, Base62UrlShortener urlShortener) {
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

}
