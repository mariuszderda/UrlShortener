package pl.mariuszderda.urlshortener.service;

import org.springframework.stereotype.Service;
import pl.mariuszderda.urlshortener.exception.UrlExpiredException;
import pl.mariuszderda.urlshortener.exception.UrlNotFoundException;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;
import pl.mariuszderda.urlshortener.repository.UrlRepository;

import static java.time.LocalDateTime.now;

@Service
public class UrlReadService {

    private final UrlRepository urlRepository;


    public UrlReadService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String resolve (String shortCode){
        var originalUrl = urlRepository.findById(shortCode);
        if (originalUrl.isEmpty()){
            throw new UrlNotFoundException("Url not found.");
        }

        ShortenedUrl url = originalUrl.get();
        if(url.getExpiresAt().isBefore(now())){
            throw new UrlExpiredException("Url time expires.");
        }
        else {
            return url.getOriginalUrl();
        }
    }
}
