package pl.mariuszderda.urlshortener.dto;

import java.time.LocalDateTime;

public class BannedUrlEvent {
    private String url;
    private String bannedWorld;
    private LocalDateTime detectedAt;

    public BannedUrlEvent(String url, String bannedWorld, LocalDateTime detectedAt) {
        this.url = url;
        this.bannedWorld = bannedWorld;
        this.detectedAt = detectedAt;
    }

    public String getUrl() {
        return url;
    }

    public String getBannedWorld() {
        return bannedWorld;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }
}
