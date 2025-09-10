package dan.dev.urlshortener.url.repositories;

import dan.dev.urlshortener.url.models.ShortenUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShortenUrlRepository extends MongoRepository<ShortenUrl, String> {
    Optional<ShortenUrl> findFirstByCode(String code);
}
