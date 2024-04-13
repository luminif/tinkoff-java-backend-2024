package edu.java.bot.services;

import edu.java.bot.api.components.LinkUpdateRequest;
import edu.java.bot.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@RequiredArgsConstructor
public class ScrapperQueueListener {
    private final UpdateService updateService;
    private final ApplicationConfig config;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final Logger logger = LogManager.getLogger();

    @KafkaListener(topics = "${app.kafka.bad-topic-name}", groupId = "${app.kafka.group-id}")
    public void listen(LinkUpdateRequest update) {
        try {
            updateService.handle(update);
        } catch (Exception e) {
            logger.info(e.getMessage());
            kafkaTemplate.send(config.kafka().badTopicName(), update);
        }
    }
}
