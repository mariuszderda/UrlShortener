package pl.mariuszderda.urlshortener.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mariuszderda.urlshortener.model.ShortenedUrl;
import pl.mariuszderda.urlshortener.repository.UrlRepository;
import pl.mariuszderda.urlshortener.util.Base62Encoder;

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

    private UrlWriteService urlShortenerService;
    private BannedWordCheckerService bannedWordCheckerService;

    @BeforeEach
    void setup() {
        urlShortenerService = new UrlWriteService(urlRepository, base62Encoder, 60, bannedWordCheckerService);
    }

    @Test
    void shouldShortenUrl() {
        // given
        when(base62Encoder.encode(0L)).thenReturn("0");

        // when
        ShortenedUrl result = urlShortenerService.shorten("https://example.com");

        // then
        Assertions.assertEquals("0", result.getShortCode());
        Assertions.assertEquals("https://example.com", result.getOriginalUrl());
        verify(urlRepository).save(any(ShortenedUrl.class));
    }

    @Test
    void shouldReturnOriginalUrlWithHttps() {
        // given
        when(base62Encoder.encode(0L)).thenReturn("0");

        // when
        ShortenedUrl result = urlShortenerService.shorten("example.com");

        // then
        assertTrue(result.getOriginalUrl().startsWith("https://"));    }
}