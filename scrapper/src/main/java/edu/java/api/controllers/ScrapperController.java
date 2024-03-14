package edu.java.api.controllers;

import edu.java.api.components.AddLinkRequest;
import edu.java.api.components.LinkResponse;
import edu.java.api.components.ListLinksResponse;
import edu.java.api.components.RemoveLinkRequest;
import edu.java.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapperController {
    private final UserService userService;

    @PostMapping("/tg-chat/{id}")
    public String registerChat(@PathVariable("id") Long id) {
        userService.registerChat(id);
        return "Чат зарегистрирован";
    }

    @DeleteMapping("/tg-chat/{id}")
    public String removeChat(@PathVariable("id") Long id) {
        userService.removeChat(id);
        return "Чат успешно удалён";
    }

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        List<LinkResponse> responseList = userService.getLinks(id);
        return new ListLinksResponse(responseList, responseList.size());
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long id, AddLinkRequest request) {
        return userService.addLink(id, request.link());
    }

    @DeleteMapping("/links")
    public LinkResponse removeLink(@RequestHeader("Tg-Chat-Id") Long id, RemoveLinkRequest request) {
        return userService.removeLink(id, request.link());
    }
}
