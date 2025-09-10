package dan.dev.urlshortener.metrics.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import java.time.LocalDateTime;

@TimeSeries(
        collection = "shorten_url_ts",
        timeField = "timestamp"
)
public class ShortenUrlMetrics {

    @Id
    private String code;
    private long access;
    private LocalDateTime timestamp;

    public ShortenUrlMetrics(String code, long access, LocalDateTime timestamp) {
        this.code = code;
        this.access = access;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public long getAccess() {
        return access;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
