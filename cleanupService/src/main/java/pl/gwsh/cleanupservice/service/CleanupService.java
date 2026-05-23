package pl.gwsh.cleanupservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.gwsh.cleanupservice.model.ShortenedUrl;
import pl.gwsh.cleanupservice.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CleanupService {
    private static final Logger log = LoggerFactory.getLogger(CleanupService.class);

    private final UrlRepository urlRepository;

    @Value("${cleanup.strategy}")
    private String strategy;

    @Value("${cleanup.ttl-days}")
    private int ttlDays;

    public CleanupService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Scheduled(cron = "${cleanup.schedule}")
    public void cleanup() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(ttlDays);
        log.info("=== Cleanup start | strategy={} | cutoff={} ===", strategy, cutoff);

        List<ShortenedUrl> all = urlRepository.findAll();
        log.info("Total entries in DB: {}", all.size());

        List<ShortenedUrl> toDelete = all.stream()
                .filter(url -> shouldDelete(url, cutoff))
                .collect(Collectors.toList());

        log.info("Entries to delete: {}", toDelete.size());

        toDelete.forEach(url -> {
            log.info("Deleting shortcode={} | createdAt={} | lastUsedAt={}",
                    url.getShortCode(),
                    url.getCreatedAt(),
                    url.getLastUseDatColumn());
            urlRepository.delete(url);
        });

        log.info("=== Cleanup done | deleted={} ===", toDelete.size());
    }

    private boolean shouldDelete(ShortenedUrl url, LocalDateTime cutoff) {
        return switch (strategy) {
            case "created" -> url.getCreatedAt() != null && url.getCreatedAt().isBefore(cutoff);
            case "last-used" -> {
                if (url.getLastUseDatColumn() == null) {
                    yield url.getCreatedAt() != null && url.getCreatedAt().isBefore(cutoff);
                }
                yield url.getLastUseDatColumn().isBefore(cutoff);
            }
            default -> {
                log.warn("Unknown strategy: {}. No entries deleted.", strategy);
                yield false;
            }
        };
    }
}
