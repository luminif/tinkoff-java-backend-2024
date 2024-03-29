package edu.java.configuration;

import edu.java.dao.JdbcChatDao;
import edu.java.dao.JdbcLinkDao;
import edu.java.services.ChatService;
import edu.java.services.LinkService;
import edu.java.services.jdbc.JdbcChatService;
import edu.java.services.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcConfiguration {
    @Bean
    public ChatService jdbcChatService(JdbcChatDao jdbcChatDao) {
        return new JdbcChatService(jdbcChatDao);
    }

    @Bean
    public LinkService jdbcLinkService(JdbcLinkDao jdbcLinkDao, JdbcChatService jdbcChatService) {
        return new JdbcLinkService(jdbcLinkDao, jdbcChatService);
    }
}
