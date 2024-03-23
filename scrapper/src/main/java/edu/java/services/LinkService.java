package edu.java.services;

import java.util.List;

public interface LinkService {
    String add(Long chatId, String url);

    String remove(Long chatId, String url);

    List<String> findLinksById(Long chatId);

    Long findLinkIdByUrl(String link);

    List<String> findOutdatedLinks(Long interval);

    List<Long> findIdsByLinkId(Long linkId);

    void setLastUpdate(Long linkId);
}
