package edu.java.services.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.entities.Link;
import edu.java.exceptions.LinkAlreadyAddedException;
import edu.java.exceptions.LinkNotFoundException;
import edu.java.services.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao jdbcLinkDao;
    private final JdbcChatService jdbcChatService;

    @Override
    public Link add(Long chatId, Link link) {
        jdbcChatService.isChatExists(chatId);
        List<Link> links = jdbcLinkDao.findLinksById(chatId);

        for (var url : links) {
            if (url.getLink().equals(link.getLink())) {
                throw new LinkAlreadyAddedException("Ссылка уже добавлена в список отслеживаемых");
            }
        }

        jdbcLinkDao.add(link, chatId);
        return link;
    }

    @Override
    public Link remove(Long chatId, Link link) {
        jdbcChatService.isChatExists(chatId);

        if (!jdbcLinkDao.isUrlExists(link.getLink())) {
            throw new LinkNotFoundException("Такой ссылки нет");
        }

        jdbcLinkDao.delete(jdbcLinkDao.findLinkIdByUrl(link), chatId);
        return link;
    }

    @Override
    public List<Link> findLinksById(Long chatId) {
        jdbcChatService.isChatExists(chatId);
        return jdbcLinkDao.findLinksById(chatId);
    }

    @Override
    public Long findLinkIdByUrl(Link link) {
        return jdbcLinkDao.findLinkIdByUrl(link);
    }

    @Override
    public List<Link> findOutdatedLinks(Long minutes) {
        return jdbcLinkDao.findOutdatedLinks(minutes);
    }

    @Override
    public List<Long> findIdsByLinkId(Long linkId) {
        return jdbcLinkDao.findIdsByLinkId(linkId);
    }

    @Override
    public void setLastUpdate(Long linkId) {
        jdbcLinkDao.setLastUpdate(linkId);
    }
}
