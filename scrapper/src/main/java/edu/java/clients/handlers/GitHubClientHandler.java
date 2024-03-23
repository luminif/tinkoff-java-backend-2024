package edu.java.clients.handlers;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubCommitResponse;
import edu.java.clients.github.GitHubResponse;
import edu.java.dao.JdbcLinkDao;
import java.time.OffsetDateTime;
import java.util.List;
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
        OffsetDateTime lastUpdate = jdbcLinkDao.getLastUpdate(link);
        var text = new StringBuilder();

        GitHubResponse response = gitHubClient.fetchRepository(name, repository);

        if (response.pushedAt().isAfter(lastUpdate)) {
            text.append("обновление в репозитории %s".formatted(link)).append("\n");
        }

        List<GitHubCommitResponse> commitResponses = gitHubClient.fetchCommit(name, repository, lastUpdate);
        int commitsCount = 0;

        for (var commitResponse : commitResponses) {
            if (commitResponse.commit().author().date().isAfter(lastUpdate)) {
                commitsCount++;
            }
        }

        text.append("в репозитории %s новый коммит".formatted(link)).append(commitsCount == 1 ? "" : "ы").append("\n");
        return text.toString();
    }
}
