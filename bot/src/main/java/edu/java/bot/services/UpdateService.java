package edu.java.bot.services;

import edu.java.bot.api.components.LinkUpdateRequest;
import edu.java.bot.exceptions.UpdateAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    private final List<LinkUpdateRequest> requests = new ArrayList<>();

    public void handle(LinkUpdateRequest linkUpdateRequest) {
        if (requests.contains(linkUpdateRequest)) {
            throw new UpdateAlreadyExistsException("Обновление уже добавлено");
        }

        requests.add(linkUpdateRequest);
    }
}
