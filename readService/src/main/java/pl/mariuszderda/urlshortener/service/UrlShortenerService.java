package pl.mariuszderda.urlshortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mariuszderda.urlshortener.exception.UrlExpiredException;
import pl.mariuszderda.urlshortener.exception.UrlNotFoundException;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;
import pl.mariuszderda.urlshortener.repository.UrlRepository;
import pl.mariuszderda.urlshortener.util.Base62Encoder;

import java.util.concurrent.atomic.AtomicLong;

import static java.time.LocalDateTime.now;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;

    private final AtomicLong counter = new AtomicLong(0);
    private final long ttlMinutes;

    public UrlShortenerService(UrlRepository urlRepository, Base62Encoder base62Encoder,
                               @Value("${url.shortener.ttl}") long ttlMinutes) {
        this.urlRepository = urlRepository;
        this.base62Encoder = base62Encoder;
        this.ttlMinutes = ttlMinutes;
    }

    public ShortenedUrl shorten(String originalUrl) {

        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")){
            originalUrl = "https://" + originalUrl;
        }
        long id = counter.getAndIncrement();
        var shortCode = base62Encoder.encode(id);
        var shortUrl = new ShortenedUrl(
                shortCode,
                originalUrl,
                now(),
                now().plusMinutes(ttlMinutes)
        );
        urlRepository.save(shortUrl);
        return shortUrl;
    }

    public String resolve (String shortCode){
        var originalUrl = urlRepository.findByShortCode(shortCode);
        if (originalUrl.isEmpty()){
            throw new UrlNotFoundException("Url not found.");
        }

        ShortenedUrl url = originalUrl.get();
        if(url.expiresAt().isBefore(now())){
            throw new UrlExpiredException("Url time expires.");
        }
        else {
            return url.originalUrl();
        }
    }
}
