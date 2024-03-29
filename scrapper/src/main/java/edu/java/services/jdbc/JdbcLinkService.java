package edu.java.services.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.exceptions.LinkAlreadyAddedException;
import edu.java.exceptions.LinkNotFoundException;
import edu.java.services.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao jdbcLinkDao;
    private final JdbcChatService jdbcChatService;

    @Override
    public String add(Long chatId, String url) {
        jdbcChatService.isChatExists(chatId);
        List<String> links = jdbcLinkDao.findLinksById(chatId);

        for (String link : links) {
            if (link.equals(url)) {
                throw new LinkAlreadyAddedException("Ссылка уже добавлена в список отслеживаемых");
            }
        }

        jdbcLinkDao.add(url, chatId);
        return url;
    }

    @Override
    public String remove(Long chatId, String url) {
        jdbcChatService.isChatExists(chatId);

        if (!jdbcLinkDao.isUrlExists(url)) {
            throw new LinkNotFoundException("Такой ссылки нет");
        }

        jdbcLinkDao.delete(jdbcLinkDao.findLinkIdByUrl(url), chatId);
        return url;
    }

    @Override
    public List<String> findLinksById(Long chatId) {
        jdbcChatService.isChatExists(chatId);
        return jdbcLinkDao.findLinksById(chatId);
    }

    @Override
    public Long findLinkIdByUrl(String link) {
        return jdbcLinkDao.findLinkIdByUrl(link);
    }

    @Override
    public List<String> findOutdatedLinks(Long minutes) {
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
