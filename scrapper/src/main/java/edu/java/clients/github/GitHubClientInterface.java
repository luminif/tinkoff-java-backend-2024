package edu.java.clients.github;

import java.time.OffsetDateTime;
import java.util.List;

public interface GitHubClientInterface {
    GitHubResponse fetchRepository(String username, String repositoryName);

    List<GitHubCommitResponse> fetchCommit(String username, String repositoryName, OffsetDateTime date);
}
