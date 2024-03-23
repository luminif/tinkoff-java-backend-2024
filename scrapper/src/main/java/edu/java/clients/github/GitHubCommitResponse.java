package edu.java.clients.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubCommitResponse(
    @JsonProperty("commit")
    Commit commit
) {
    public record Commit(
        @JsonProperty("author")
        Author author
    ) {
        public record Author(
            @JsonProperty("date")
            OffsetDateTime date
        ) {
        }
    }
}
