package edu.java.schedulers;

import edu.java.api.components.LinkUpdateRequest;
import edu.java.clients.BotWebClient;
import edu.java.clients.handlers.ClientHandler;
import edu.java.services.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    @Value("${app.scheduler.time-for-check}")
    private Long minutes;
    private final Logger logger = LogManager.getLogger();
    private final LinkService linkService;
    private final List<ClientHandler> clientHandlers;
    private BotWebClient botWebClient;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    void update() {
        List<String> outdatedLinks = linkService.findOutdatedLinks(minutes);

        outdatedLinks.forEach(link -> {
            Long linkId = linkService.findLinkIdByUrl(link);
            String host = link.split("/+")[1];
            botWebClient = new BotWebClient(host);

            for (var clientHandler : clientHandlers) {
                if (clientHandler.supports(host)) {
                    try {
                        botWebClient.sendUpdate(new LinkUpdateRequest(
                            linkId,
                            new URI(link),
                            clientHandler.getUpdate(link),
                            linkService.findIdsByLinkId(linkId)
                        ));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }

                    linkService.setLastUpdate(linkId);
                }
            }
        });

        logger.info("Starting scheduler...");
    }
}
