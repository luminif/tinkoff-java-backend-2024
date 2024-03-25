package edu.java.clients.stackoverflow;

public interface StackOverflowClientInterface {
    StackOverflowResponse fetchQuestion(Long questionId);

    StackOverflowResponse fetchNewAnswer(Long questionId);
}
