package edu.java.services.jpa;

import edu.java.entities.Link;
import edu.java.exceptions.LinkAlreadyAddedException;
import edu.java.exceptions.LinkNotFoundException;
import edu.java.repositories.JpaLinkRepository;
import edu.java.services.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaChatService jpaChatService;
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public Link add(Long chatId, Link link) {
        jpaChatService.isChatExists(chatId);
        List<Link> links = jpaLinkRepository.findLinksById(chatId);

        for (var url : links) {
            if (url.equals(link)) {
                throw new LinkAlreadyAddedException("Ссылка уже добавлена в список отслеживаемых");
            }
        }

        Link addLink = jpaLinkRepository.save(link);
        jpaLinkRepository.add(addLink.getId(), chatId);
        return addLink;
    }

    @Override
    public Link remove(Long chatId, Link link) {
        jpaChatService.isChatExists(chatId);

        if (!jpaLinkRepository.isUrlExists(link.getLink())) {
            throw new LinkNotFoundException("Такой ссылки нет");
        }

        jpaLinkRepository.delete(link.getId(), chatId);
        jpaLinkRepository.delete(link);

        return link;
    }

    @Override
    public List<Link> findLinksById(Long chatId) {
        return jpaLinkRepository.findLinksById(chatId);
    }

    @Override
    public Long findLinkIdByUrl(Link link) {
        return jpaLinkRepository.findLinkIdByUrl(link.getLink());
    }

    @Override
    public List<Link> findOutdatedLinks(Long minutes) {
        return jpaLinkRepository.findOutdatedLinks(minutes);
    }

    @Override
    public List<Long> findIdsByLinkId(Long linkId) {
        return jpaLinkRepository.findIdsByLinkId(linkId);
    }

    @Override
    public void setLastUpdate(Long linkId) {
        jpaLinkRepository.setLastUpdate(linkId);
    }
}
