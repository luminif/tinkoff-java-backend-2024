package edu.java.clients.handlers;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubCommitResponse;
import edu.java.clients.github.GitHubResponse;
import edu.java.dao.JdbcLinkDao;
import edu.java.entities.Link;
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
    public String getUpdate(Link link) {
        String[] segments = link.getLink().split("/+");
        String name = segments[NAME_INDEX];
        String repository = segments[REP_INDEX];
        OffsetDateTime lastUpdate = jdbcLinkDao.getLastUpdate(link.getLink());
        var text = new StringBuilder();

        GitHubResponse response = gitHubClient.fetchRepository(name, repository);

        if (response.pushedAt().isAfter(lastUpdate)) {
            text.append("обновление в репозитории. ");
        }

        List<GitHubCommitResponse> commitResponses = gitHubClient.fetchCommit(name, repository, lastUpdate);

        for (var commitResponse : commitResponses) {
            var current = commitResponse.commit().author();
            if (current.date().isAfter(lastUpdate)) {
                text.append("(новый коммит от %s)".formatted(current.name())).append("\n");
            }
        }

        return text.toString();
    }
}
