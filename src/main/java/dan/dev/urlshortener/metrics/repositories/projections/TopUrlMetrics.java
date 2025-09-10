package dan.dev.urlshortener.metrics.repositories.projections;

public record TopUrlMetrics(
        String shortCode,
        long totalClicks
) {
}
