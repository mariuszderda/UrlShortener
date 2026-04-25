package pl.mariuszderda.urlshortener.repository;

import org.springframework.stereotype.Repository;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUrlRepository implements UrlRepository {

    private final ConcurrentHashMap<String, ShortenedUrl> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public void save(ShortenedUrl url) {
        inMemoryDatabase.put(url.shortCode(), url);
    }

    @Override
    public Optional<ShortenedUrl> findByShortCode(String code) {
        return Optional.ofNullable(inMemoryDatabase.get(code));
    }
}
