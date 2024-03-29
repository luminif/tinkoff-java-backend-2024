package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponse(List<ItemResponse> items) {
    public record ItemResponse(
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate,
        @JsonProperty("creation_date")
        OffsetDateTime creationDate,
        @JsonProperty("question_id")
        Long questionId,
        @JsonProperty("owner")
        Owner owner
    ) {
        public record Owner(
            @JsonProperty("display_name")
            String name
        ) {

        }
    }
}
