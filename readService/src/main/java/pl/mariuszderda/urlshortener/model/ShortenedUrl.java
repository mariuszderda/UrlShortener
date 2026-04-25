package pl.mariuszderda.urlshortener.model;


import java.time.LocalDateTime;

public record ShortenedUrl(
        String shortCode,
        String originalUrl,
        LocalDateTime createdAt,
        LocalDateTime expiresAt) {

}
