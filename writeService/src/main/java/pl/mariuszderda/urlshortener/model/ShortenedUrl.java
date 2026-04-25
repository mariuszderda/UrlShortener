package pl.mariuszderda.urlshortener.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_shorten")
public class ShortenedUrl{
        @Id String shortCode;
        @Column
        String originalUrl;
        @Column
        LocalDateTime createdAt;
        @Column
        LocalDateTime expiresAt;

    protected ShortenedUrl() {
    }

    public ShortenedUrl(String shortCode, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
