package pl.gwsh.cleanupservice.repository;


import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.gwsh.cleanupservice.model.ShortenedUrl;

import java.util.Optional;

public interface UrlRepository extends CassandraRepository<ShortenedUrl, String> {
    Optional<ShortenedUrl> findById(String id);
}
