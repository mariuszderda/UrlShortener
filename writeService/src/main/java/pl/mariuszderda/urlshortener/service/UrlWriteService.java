package pl.mariuszderda.urlshortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mariuszderda.urlshortener.exception.BannedUrlException;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;
import pl.mariuszderda.urlshortener.repository.UrlRepository;
import pl.mariuszderda.urlshortener.util.Base62Encoder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.time.LocalDateTime.now;

@Service
public class UrlWriteService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;

    private final AtomicLong counter = new AtomicLong(0);
    private final long ttlMinutes;
    private final BannedWordCheckerService bannedWordCheckerService;

    public UrlWriteService(UrlRepository urlRepository, Base62Encoder base62Encoder,
                           @Value("${url.shortener.ttl}") long ttlMinutes, BannedWordCheckerService bannedWordCheckerService) {
        this.urlRepository = urlRepository;
        this.base62Encoder = base62Encoder;
        this.ttlMinutes = ttlMinutes;
        this.bannedWordCheckerService = bannedWordCheckerService;
    }

    public ShortenedUrl shorten(String originalUrl) {
        Optional<String> banned = bannedWordCheckerService.check(originalUrl);
        if(banned.isPresent()){
            throw new BannedUrlException("Url contains banned word: " + banned.get());
        }

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
