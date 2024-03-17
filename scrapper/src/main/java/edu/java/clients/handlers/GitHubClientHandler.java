package edu.java.clients.handlers;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubResponse;
import edu.java.dao.JdbcLinkDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GitHubClientHandler implements ClientHandler {
    private final GitHubClient gitHubClient;
    private final JdbcLinkDao jdbcLinkDao;
    private final static int NAME_INDEX = 2;
    private final static int REP_INDEX = 3;

    @Override
    public boolean supports(String host) {
        return host.equals("github.com");
    }

    @Override
    public String getUpdate(String link) {
        String[] segments = link.split("/+");
        String name = segments[NAME_INDEX];
        String repository = segments[REP_INDEX];

        GitHubResponse response = gitHubClient.fetchRepository(name, repository);

        if (response.pushedAt().isAfter(jdbcLinkDao.getLastUpdate(link))) {
            return "обновление в репозитории %s".formatted(link);
        }

        return EMPTY;
    }
}
