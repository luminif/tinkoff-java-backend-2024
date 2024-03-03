package edu.java.bot.api.controllers;

import edu.java.bot.api.components.LinkUpdateRequest;
import edu.java.bot.services.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final UpdateService updateService;

    @PostMapping("/updates")
    public String update(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        updateService.handle(linkUpdateRequest);
        return "Обновление обработано";
    }
}
