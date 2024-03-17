package edu.java.clients.handlers;

import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowResponse;
import edu.java.dao.JdbcLinkDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StackOverflowClientHandler implements ClientHandler {
    private final StackOverflowClient stackOverflowClient;
    private final JdbcLinkDao jdbcLinkDao;
    private final static int ID_INDEX = 3;

    @Override
    public boolean supports(String host) {
        return host.equals("stackoverflow.com");
    }

    @Override
    public String getUpdate(String link) {
        String[] segments = link.split("/+");
        Long postId = Long.valueOf(segments[ID_INDEX]);

        StackOverflowResponse response = stackOverflowClient.fetchQuestion(postId);

        if (response.lastActivityDate().isAfter(jdbcLinkDao.getLastUpdate(link))) {
            return "обновление в вопросе %s".formatted(link);
        }

        return EMPTY;
    }
}
