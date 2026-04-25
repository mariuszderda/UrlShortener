package pl.mariuszderda.urlshortener.repository;

import pl.mariuszderda.urlshortener.model.ShortenedUrl;

import java.util.Optional;

public interface UrlRepository {
    void save(ShortenedUrl url);
    Optional<ShortenedUrl> findByShortCode(String code);
}
