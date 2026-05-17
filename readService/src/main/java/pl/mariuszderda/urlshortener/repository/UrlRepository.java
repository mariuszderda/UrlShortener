package pl.mariuszderda.urlshortener.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;

import java.util.Optional;

public interface UrlRepository extends CassandraRepository<ShortenedUrl, String> {
    Optional<ShortenedUrl> findById(String id);
}
