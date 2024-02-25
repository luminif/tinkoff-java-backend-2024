package edu.java.clients.github;

public interface GitHubClientInterface {
    GitHubResponse fetchRepository(String username, String repositoryName);
}
