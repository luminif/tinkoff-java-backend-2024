package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBot telegramBot(@NotNull ApplicationConfig applicationConfig) {
        return new TelegramBot(applicationConfig.telegramToken());
    }
}
