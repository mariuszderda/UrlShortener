package pl.mariuszderda.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<ShortenedUrl, String> {
    Optional<ShortenedUrl> findByShortCode(String code);
}
