package pl.gwsh.moderationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ModerationConsumer {
    private static final Logger log = LoggerFactory.getLogger(ModerationConsumer.class);

    @KafkaListener(topics = "${kafka.topic.banned}", groupId = "moderation-group")
    public void consume(String message){
        log.info("=== BANNED URL EVENT RECEIVED ===");
        log.info("Payload: {}", message);
        log.info("=================================");
    }
}
