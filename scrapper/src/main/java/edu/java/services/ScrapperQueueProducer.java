package edu.java.services;

import edu.java.api.components.LinkUpdateRequest;
import edu.java.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final ApplicationConfig config;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    public void sendUpdate(LinkUpdateRequest update) {
        kafkaTemplate.send(config.kafka().topicName(), update);
    }
}
