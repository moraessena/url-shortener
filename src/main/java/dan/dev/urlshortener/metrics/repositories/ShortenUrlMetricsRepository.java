package dan.dev.urlshortener.metrics.repositories;

import dan.dev.urlshortener.metrics.models.ShortenUrlMetrics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortenUrlMetricsRepository extends MongoRepository<ShortenUrlMetrics, String>, ShortenUrlMetricsCustomRepository {
}
