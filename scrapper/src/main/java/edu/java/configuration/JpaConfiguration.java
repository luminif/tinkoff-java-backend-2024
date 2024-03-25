package edu.java.configuration;

import edu.java.repositories.JpaChatRepository;
import edu.java.repositories.JpaLinkRepository;
import edu.java.services.ChatService;
import edu.java.services.LinkService;
import edu.java.services.jpa.JpaChatService;
import edu.java.services.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaConfiguration {
    @Bean
    public ChatService jpaChatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }

    @Bean
    public LinkService jpaLinkService(JpaChatService jpaChatService, JpaLinkRepository jpaLinkRepository) {
        return new JpaLinkService(jpaChatService, jpaLinkRepository);
    }
}
