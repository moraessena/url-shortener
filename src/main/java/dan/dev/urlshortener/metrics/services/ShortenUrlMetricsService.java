package dan.dev.urlshortener.metrics.services;

import dan.dev.urlshortener.metrics.models.ShortenUrlMetrics;
import dan.dev.urlshortener.metrics.repositories.ShortenUrlMetricsRepository;
import dan.dev.urlshortener.url.events.ShortenUrlAccessedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class ShortenUrlMetricsService {

    private final ShortenUrlMetricsRepository shortenUrlMetricsRepository;

    ShortenUrlMetricsService(ShortenUrlMetricsRepository shortenUrlMetricsRepository) {
        this.shortenUrlMetricsRepository = shortenUrlMetricsRepository;
    }

    @EventListener
    void onShortenUrlAccessedEvent(ShortenUrlAccessedEvent event) {
        var metric = new ShortenUrlMetrics(event.code(), 1, LocalDateTime.now());
        shortenUrlMetricsRepository.save(metric);
    }

}
