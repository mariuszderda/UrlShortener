package pl.gwsh.cleanupservice.model;


import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Table("urlshorten")
public class ShortenedUrl {
    @PrimaryKey
    String shortcode;
    @Column
    String originalUrl;
    @Column
    LocalDateTime createdAt;
    @Column
    LocalDateTime expiresAt;

    @Column("lastusedatcolumn")
    LocalDateTime lastUseDatColumn;

    protected ShortenedUrl() {
    }

    public ShortenedUrl(String shortcode, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.shortcode = shortcode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.lastUseDatColumn = createdAt;
    }

    public String getShortCode() {
        return shortcode;
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

    public LocalDateTime getLastUseDatColumn() {
        return lastUseDatColumn;
    }

    public void setLastUseDatColumn(LocalDateTime lastUseDatColumn) {
        this.lastUseDatColumn = lastUseDatColumn;
    }

}
