package edu.java.clients.handlers;

import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowResponse;
import edu.java.dao.JdbcLinkDao;
import java.time.OffsetDateTime;
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
        Long questionId = Long.valueOf(segments[ID_INDEX]);
        OffsetDateTime lastUpdate = jdbcLinkDao.getLastUpdate(link);
        var text = new StringBuilder();

        StackOverflowResponse response = stackOverflowClient.fetchQuestion(questionId);

        for (var item : response.items()) {
            if (item.lastActivityDate().isAfter(lastUpdate)) {
                text.append("обновление в вопросе %s".formatted(link)).append("\n");
            }
        }

        response = stackOverflowClient.fetchNewAnswer(questionId);
        int answersCount = 0;

        for (var item : response.items()) {
            if (item.creationDate().isAfter(lastUpdate)) {
                answersCount++;
            }
        }

        text.append("новые ответы в вопросе %s: %d".formatted(link, answersCount)).append("\n");
        return text.toString();
    }
}
