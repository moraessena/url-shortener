package dan.dev.urlshortener.metrics.services;

import dan.dev.urlshortener.metrics.models.ShortenUrlMetrics;
import dan.dev.urlshortener.metrics.repositories.ShortenUrlMetricsRepository;
import dan.dev.urlshortener.metrics.repositories.projections.DailyMetrics;
import dan.dev.urlshortener.metrics.repositories.projections.HourlyMetrics;
import dan.dev.urlshortener.metrics.repositories.projections.TopUrlMetrics;
import dan.dev.urlshortener.url.events.ShortenUrlAccessedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShortenUrlMetricsService {

    private final ShortenUrlMetricsRepository shortenUrlMetricsRepository;

    ShortenUrlMetricsService(ShortenUrlMetricsRepository shortenUrlMetricsRepository) {
        this.shortenUrlMetricsRepository = shortenUrlMetricsRepository;
    }

    @EventListener
    void onShortenUrlAccessedEvent(ShortenUrlAccessedEvent event) {
        var metric = new ShortenUrlMetrics(event.code(), 1, LocalDateTime.now());
        shortenUrlMetricsRepository.insert(metric);
    }

    public List<DailyMetrics> getDailyMetrics(String code, int days) {
        return shortenUrlMetricsRepository.getDailyMetrics(code, days);
    }

    public List<HourlyMetrics> getHourlyMetrics(String code) {
        return shortenUrlMetricsRepository.getHourlyMetrics(code);
    }

    public List<TopUrlMetrics> getTopUrls(int limit) {
        return shortenUrlMetricsRepository.getTopUrls(limit);
    }
}
