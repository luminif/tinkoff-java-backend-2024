package edu.java.services;

import edu.java.entities.Link;
import java.util.List;

public interface LinkService {
    Link add(Long chatId, Link link);

    Link remove(Long chatId, Link link);

    List<Link> findLinksById(Long chatId);

    Long findLinkIdByUrl(Link link);

    List<Link> findOutdatedLinks(Long minutes);

    List<Long> findIdsByLinkId(Long linkId);

    void setLastUpdate(Long linkId);
}
