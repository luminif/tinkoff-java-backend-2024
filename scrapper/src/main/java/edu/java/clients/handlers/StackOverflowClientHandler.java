package edu.java.clients.handlers;

import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowResponse;
import edu.java.dao.JdbcLinkDao;
import edu.java.entities.Link;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public String getUpdate(Link link) {
        String[] segments = link.getLink().split("/+");
        Long questionId = Long.valueOf(segments[ID_INDEX]);
        OffsetDateTime lastUpdate = jdbcLinkDao.getLastUpdate(link.getLink());
        var text = new StringBuilder();

        StackOverflowResponse response = stackOverflowClient.fetchQuestionRetry(questionId);

        for (var item : response.items()) {
            if (item.lastActivityDate().isAfter(lastUpdate)) {
                text.append("обновление в вопросе. ");
            }
        }

        response = stackOverflowClient.fetchNewAnswerRetry(questionId);
        List<String> users = new ArrayList<>();

        for (var item : response.items()) {
            if (item.creationDate().isAfter(lastUpdate)) {
                users.add(item.owner().name());
            }
        }

        if (!users.isEmpty()) {
            text
                .append(users.size() > 1 ? "%d новых ответов от:".formatted(users.size())
                    : "Новый ответ в вопросе от %s.".formatted(users.get(0))).append("\n");
            for (int i = 0; users.size() > 1 && i < users.size(); i++) {
                text.append("%d) %s".formatted(i + 1, users.get(i)));
            }
        }

        return text.toString();
    }
}
