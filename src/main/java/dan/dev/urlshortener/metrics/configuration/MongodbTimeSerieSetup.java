package dan.dev.urlshortener.metrics.configuration;

import dan.dev.urlshortener.metrics.models.ShortenUrlMetrics;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongodbTimeSerieSetup implements ApplicationListener<ApplicationReadyEvent> {

    private final MongoTemplate mongoTemplate;

    public MongodbTimeSerieSetup(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var collectionExists = mongoTemplate.collectionExists(ShortenUrlMetrics.class);
        if (!collectionExists) {
            mongoTemplate.createCollection(ShortenUrlMetrics.class);
        }
    }
}
