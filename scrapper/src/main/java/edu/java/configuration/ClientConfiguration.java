package edu.java.configuration;

import edu.java.clients.BotWebClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value("${api.github.base-url}")
    private String githubBaseUrl;
    @Value("${api.stackoverflow.base-url}")
    private String stackoverflowBaseUrl;
    @Value("${api.bot.base-url}")
    private String botWebClientBaseUrl;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(githubBaseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(stackoverflowBaseUrl);
    }

    @Bean
    public BotWebClient botWebClient() {
        return new BotWebClient(botWebClientBaseUrl);
    }
}
