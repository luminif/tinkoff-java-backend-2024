package edu.java.bot.configuration;

import edu.java.bot.clients.ScrapperWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value("${api.scrapper.base-url}")
    private String baseUrl;

    @Bean
    public ScrapperWebClient scrapperWebClient() {
        return new ScrapperWebClient(baseUrl);
    }
}
