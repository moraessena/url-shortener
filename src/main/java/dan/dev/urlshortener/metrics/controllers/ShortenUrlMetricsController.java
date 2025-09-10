package dan.dev.urlshortener.metrics.controllers;

import dan.dev.urlshortener.metrics.repositories.projections.HourlyMetrics;
import dan.dev.urlshortener.metrics.services.ShortenUrlMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/metrics")
public class ShortenUrlMetricsController {

    private final ShortenUrlMetricsService shortenUrlMetricsService;

    public ShortenUrlMetricsController(ShortenUrlMetricsService shortenUrlMetricsService) {
        this.shortenUrlMetricsService = shortenUrlMetricsService;
    }

    @GetMapping("/{code}/daily")
    public ResponseEntity<?> getDailyMetrics(
            @PathVariable String code,
            @RequestParam(defaultValue = "30") int days) {
        if (days > 60)
            return ResponseEntity.badRequest().body("days cannot be more than 60");
        return ResponseEntity.ok(shortenUrlMetricsService.getDailyMetrics(code, days));
    }

    @GetMapping("/{code}/hourly")
    public ResponseEntity<List<HourlyMetrics>> getHourlyMetrics(@PathVariable String code) {
        return ResponseEntity.ok(shortenUrlMetricsService.getHourlyMetrics(code));
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopUrls(
            @RequestParam(defaultValue = "10") int limit) {
        if (limit > 100)
            return ResponseEntity.badRequest().body("limit cannot be more than 100");
        return ResponseEntity.ok(shortenUrlMetricsService.getTopUrls(limit));
    }
}
