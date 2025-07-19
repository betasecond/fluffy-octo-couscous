package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;
import edu.jimei.praesidium.exception.AIServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the InteractiveQueryService.
 * This class is responsible for orchestrating the process of answering a user's query.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InteractiveQueryServiceImpl implements InteractiveQueryService {

    private final ChatClient chatClient;

    public static record AiResponse(String suggestedAnswer, double confidence, List<String> keywordAnalysis, boolean needsHumanReview) {
    }

    /**
     * Submits a user's query to the AI service for a response.
     * <p>
     * Note: This is a placeholder implementation. The actual implementation would involve
     * retrieving context from a vector store, building a comprehensive prompt,
     * and performing more sophisticated parsing of the AI's response.
     *
     * @param request The user's query request.
     * @return A QueryResponse containing the AI's suggested answer and analysis.
     * @throws AIServiceException if there is an error communicating with the AI service.
     */
    @Override
    public QueryResponse submitQuery(QueryRequest request) {
        log.info("Submitting query to AI for user []: '{}'", request.getQuery());

        try {
            var systemPrompt = """
                    You are a professional customer service assistant. Your task is to analyze the user's query and provide a helpful response.
                    Based on the user's query, you need to generate a response in JSON format with the following fields:
                    - suggestedAnswer: A clear and concise answer to the user's question.
                    - confidence: A value between 0.0 and 1.0, representing your confidence in the answer.
                    - keywordAnalysis: A list of keywords extracted from the user's query.
                    - needsHumanReview: A boolean value indicating whether the query is too complex and requires human intervention.
                    """;

            AiResponse response = chatClient.prompt()
                    .system(systemPrompt)
                    .user(request.getQuery())
                    .call()
                    .entity(AiResponse.class);

            return QueryResponse.builder()
                    .suggestedAnswer(response.suggestedAnswer())
                    .confidence(response.confidence())
                    .keywordAnalysis(response.keywordAnalysis())
                    .needsHumanReview(response.needsHumanReview())
                    .build();
        } catch (Exception e) {
            log.error("Error calling AI service for query from user []: {}", request.getQuery(), e);
            throw new AIServiceException("Failed to get response from AI service for query.", e);
        }
    }
}
