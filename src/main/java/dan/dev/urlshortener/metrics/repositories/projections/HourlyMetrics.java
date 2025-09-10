package dan.dev.urlshortener.metrics.repositories.projections;

import java.time.LocalDate;

public record HourlyMetrics(
        LocalDate date,
        int hour,
        long totalClicks
) {
}
