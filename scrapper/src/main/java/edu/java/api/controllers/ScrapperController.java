package edu.java.api.controllers;

import edu.java.api.components.AddLinkRequest;
import edu.java.api.components.LinkResponse;
import edu.java.api.components.ListLinksResponse;
import edu.java.api.components.RemoveLinkRequest;
import edu.java.services.ChatService;
import edu.java.services.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
    private final ChatService chatService;
    private final LinkService linkService;

    @PostMapping("/tg-chat/{id}")
    public String registerChat(@PathVariable("id") Long id) {
        chatService.register(id);
        return "Чат зарегистрирован";
    }

    @DeleteMapping("/tg-chat/{id}")
    public String removeChat(@PathVariable("id") Long id) {
        chatService.unregister(id);
        return "Чат успешно удалён";
    }

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        List<String> responseList = linkService.findLinksById(id);

        List<LinkResponse> linkResponses = new ArrayList<>();

        responseList.forEach(url -> {
            try {
                linkResponses.add(new LinkResponse(id, new URI(url)));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        return new ListLinksResponse(linkResponses, responseList.size());
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long id, AddLinkRequest request)
        throws URISyntaxException {
        String link = request.link().toString();
        String addLink = linkService.add(id, link);
        return new LinkResponse(id, new URI(addLink));
    }

    @DeleteMapping("/links")
    public LinkResponse removeLink(@RequestHeader("Tg-Chat-Id") Long id, RemoveLinkRequest request)
    throws URISyntaxException {
        String link = request.link().toString();
        String removeLink = linkService.remove(id, link);
        return new LinkResponse(id, new URI(removeLink));
    }
}
