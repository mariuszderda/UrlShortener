package pl.mariuszderda.urlshortener.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mariuszderda.urlshortener.exception.UrlExpiredException;
import pl.mariuszderda.urlshortener.exception.UrlNotFoundException;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;
import pl.mariuszderda.urlshortener.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlRepository urlRepository;

    private UrlReadService urlShortenerService;

    @BeforeEach
    void setup() {
        urlShortenerService = new UrlReadService(urlRepository);
    }

    @Test
    void shouldReturnNotFoundException() {
        Assertions.assertThrows(UrlNotFoundException.class, () -> urlShortenerService.resolve("1"));
    }

    @Test
    void shouldReturnExpiresException() {
        // given
        var expiredUrl = new ShortenedUrl(
                "sbc",
                "https://example.com",
                LocalDateTime.now().minusHours(2),
                LocalDateTime.now().minusHours(1)
        );
        when(urlRepository.findByShortCode("sbc")).thenReturn(Optional.of(expiredUrl));

        // when & then
        Assertions.assertThrows(UrlExpiredException.class, () -> urlShortenerService.resolve("sbc"));

    }



}