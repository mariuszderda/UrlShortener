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
import pl.mariuszderda.urlshortener.util.Base62Encoder;

import java.time.Duration;
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

    @Mock
    private Base62Encoder base62Encoder;

    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setup() {
        urlShortenerService = new UrlShortenerService(urlRepository, base62Encoder, 60);
    }

    @Test
    void shouldShortenUrl() {
        // given
        when(base62Encoder.encode(0L)).thenReturn("0");

        // when
        ShortenedUrl result = urlShortenerService.shorten("https://example.com");

        // then
        Assertions.assertEquals("0", result.shortCode());
        Assertions.assertEquals("https://example.com", result.originalUrl());
        verify(urlRepository).save(any(ShortenedUrl.class));
    }

    @Test
    void shouldReturnOriginalUrlWithHttps() {
        // given
        when(base62Encoder.encode(0L)).thenReturn("0");

        // when
        ShortenedUrl result = urlShortenerService.shorten("example.com");

        // then
        assertTrue(result.originalUrl().startsWith("https://"));
    }

    @Test
    void shouldReturnOriginalUrl() {
        // given
        when(base62Encoder.encode(0L)).thenReturn("0");

        // when
        ShortenedUrl result = urlShortenerService.shorten("example.com");
        Duration timeExpire = Duration.between(result.createdAt(), result.expiresAt());
        long minutes = timeExpire.toMinutes();
        // then
        Assertions.assertEquals(60, minutes);
        Assertions.assertEquals("https://example.com", result.originalUrl());
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