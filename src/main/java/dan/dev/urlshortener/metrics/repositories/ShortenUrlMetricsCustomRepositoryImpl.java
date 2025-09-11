package dan.dev.urlshortener.metrics.repositories;

import dan.dev.urlshortener.metrics.repositories.projections.DailyMetrics;
import dan.dev.urlshortener.metrics.repositories.projections.HourlyMetrics;
import dan.dev.urlshortener.metrics.repositories.projections.TopUrlMetrics;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
class ShortenUrlMetricsCustomRepositoryImpl implements ShortenUrlMetricsCustomRepository {

    private final MongoTemplate mongoTemplate;

    ShortenUrlMetricsCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<DailyMetrics> getDailyMetrics(String code, int days) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(days);

        DateOperators.DateToString dateToString = DateOperators.DateToString.dateToString("$timestamp").toString("%Y-%m-%d");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria
                        .where("_id").is(code)
                        .and("timestamp").gte(startDate).lte(endDate)
                ),
                Aggregation.project()
                        .and("_id").as("shortCode")
                        .and(dateToString).as("date")
                        .and("access").as("access"),
                Aggregation.group("date")
                        .sum("access").as("totalClicks")
                        .first("date").as("date"),
                Aggregation.sort(Sort.Direction.DESC, "_id")
        );

        return mongoTemplate.aggregate(aggregation, "shorten_url_ts", DailyMetrics.class)
                .getMappedResults();
    }

    @Override
    public List<HourlyMetrics> getHourlyMetrics(String code) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusHours(24);

        DateOperators.DateToString dateToString = DateOperators.DateToString.dateToString("$timestamp").toString("%Y-%m-%d");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria
                        .where("_id").is(code)
                        .and("timestamp").gte(startDate).lte(endDate)
                ),
                Aggregation.project()
                        .and("_id").as("shortCode")
                        .and(DateOperators.Hour.hour("$timestamp")).as("hour")
                        .and(dateToString).as("date")
                        .and("access").as("access"),
                Aggregation.group(Fields.fields("date", "hour"))
                        .sum("access").as("totalClicks")
                        .first("date").as("date")
                        .first("hour").as("hour"),
                Aggregation.sort(Sort.Direction.DESC, "_id")
        );

        return mongoTemplate.aggregate(aggregation, "shorten_url_ts", HourlyMetrics.class)
                .getMappedResults();
    }

    @Override
    public List<TopUrlMetrics> getTopUrls(int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("_id")
                        .sum("access").as("totalClicks")
                        .first("_id").as("shortCode"),
                Aggregation.sort(Sort.Direction.DESC, "totalClicks"),
                Aggregation.limit(limit)
        );

        return mongoTemplate.aggregate(aggregation, "shorten_url_ts", TopUrlMetrics.class)
                .getMappedResults();
    }
}
