package dan.dev.urlshortener.metrics.repositories.projections;

import java.time.LocalDate;

public record DailyMetrics(
        LocalDate date,
        long totalClicks
) {
}
