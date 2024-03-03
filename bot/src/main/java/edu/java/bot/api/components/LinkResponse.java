package edu.java.bot.api.components;

import java.net.URI;

public record LinkResponse(
    Long id,
    URI url
) {
}
