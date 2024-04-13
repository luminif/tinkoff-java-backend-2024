package edu.java.services;

import edu.java.api.components.LinkUpdateRequest;
import edu.java.clients.BotWebClient;
import edu.java.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdateSender {
    private final ApplicationConfig config;
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final BotWebClient botWebClient;

    public void sendUpdate(LinkUpdateRequest update) {
        if (config.useQueue()) {
            scrapperQueueProducer.sendUpdate(update);
        } else {
            botWebClient.sendUpdateRetry(update);
        }
    }
}
