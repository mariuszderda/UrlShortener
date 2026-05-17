package pl.mariuszderda.urlshortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;
import pl.mariuszderda.urlshortener.repository.UrlRepository;
import pl.mariuszderda.urlshortener.util.Base62Encoder;

import java.util.concurrent.atomic.AtomicLong;

import static java.time.LocalDateTime.now;

@Service
public class UrlWriteService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;

    private final AtomicLong counter = new AtomicLong(0);
    private final long ttlMinutes;

    public UrlWriteService(UrlRepository urlRepository, Base62Encoder base62Encoder,
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
        var shortcode = base62Encoder.encode(id);
        var shortUrl = new ShortenedUrl(
                shortcode,
                originalUrl,
                now(),
                now().plusMinutes(ttlMinutes)
        );
        urlRepository.save(shortUrl);
        return shortUrl;
    }
}
