package edu.java.clients.stackoverflow;

public interface StackOverflowClientInterface {
    StackOverflowResponse fetchQuestion(Long questionId);

    StackOverflowResponse fetchQuestionRetry(Long questionId);

    StackOverflowResponse fetchNewAnswer(Long questionId);

    StackOverflowResponse fetchNewAnswerRetry(Long questionId);
}
