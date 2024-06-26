package edu.java.schedulers;

import edu.java.api.components.LinkUpdateRequest;
import edu.java.clients.handlers.ClientHandler;
import edu.java.entities.Link;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdateSender;
import java.net.URI;
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
    private final LinkUpdateSender linkUpdateSender;
    private static final String DELETE_MESSAGE = "похоже, ссылки больше нет. Она будет удалена.";

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    void update() {
        List<Link> outdatedLinks = linkService.findOutdatedLinks(minutes);

        outdatedLinks.forEach(link -> {
            link.setId(linkService.findLinkIdByUrl(link));
            String host = link.getLink().split("/+")[1];

            for (var clientHandler : clientHandlers) {
                if (clientHandler.supports(host)) {
                    List<Long> tgChatIds = linkService.findIdsByLinkId(link.getId());

                    LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
                        link.getId(),
                        URI.create(link.getLink()),
                        clientHandler.getUpdate(link),
                        tgChatIds
                    );

                    linkUpdateSender.sendUpdate(linkUpdateRequest);

                    if (linkUpdateRequest.description().equals(DELETE_MESSAGE)) {
                        tgChatIds.forEach(chatId -> linkService.remove(chatId, link));
                    } else {
                        linkService.setLastUpdate(link.getId());
                    }
                }
            }
        });

        logger.info("Starting scheduler...");
    }
}
