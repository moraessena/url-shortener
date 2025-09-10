package dan.dev.urlshortener.metrics.repositories;

import dan.dev.urlshortener.metrics.repositories.projections.DailyMetrics;
import dan.dev.urlshortener.metrics.repositories.projections.HourlyMetrics;
import dan.dev.urlshortener.metrics.repositories.projections.TopUrlMetrics;

import java.util.List;

public interface ShortenUrlMetricsCustomRepository {
    List<DailyMetrics> getDailyMetrics(String code, int days);
    List<HourlyMetrics> getHourlyMetrics(String code);
    List<TopUrlMetrics> getTopUrls(int limit);
}


