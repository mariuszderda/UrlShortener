package pl.mariuszderda.urlshortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mariuszderda.urlshortener.dto.BannedUrlEvent;
import pl.mariuszderda.urlshortener.model.BannedWord;
import pl.mariuszderda.urlshortener.repository.BannedWordRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class BannedWordCheckerService {

    private static final Logger log = LoggerFactory.getLogger(BannedWordCheckerService.class);

    private final BannedWordRepository bannedWordRepository;
    private final KafkaProducer kafkaProducer;

    public BannedWordCheckerService(BannedWordRepository bannedWordRepository, KafkaProducer kafkaProducer) {
        this.bannedWordRepository = bannedWordRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public Optional<String> check(String url) {
        String urlLower = url.toLowerCase();
        List<BannedWord> banned = bannedWordRepository.findAll();

        return banned.stream()
                .map(BannedWord::getWord)
                .filter(word -> urlLower.contains(word.toLowerCase()))
                .findFirst()
                .map(word -> {
                    log.warn("Banned word detected | url={} | word{}", url, word);
                    BannedUrlEvent event = new BannedUrlEvent(url, word, LocalDateTime.now());
                    kafkaProducer.send(event);
                    return word;
                });
    }

}
