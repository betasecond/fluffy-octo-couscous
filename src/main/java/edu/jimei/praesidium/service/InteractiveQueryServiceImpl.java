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
            String response = chatClient.prompt()
                    .user(request.getQuery()+"\n\n"+"请根据以上内容，给出最符合用户意图的回答。需要使用中文回答。")
                    .call()
                    .content();


            // Placeholder mapping to QueryResponse
            return QueryResponse.builder()
                    .suggestedAnswer(response)
                    .confidence(0.9) // Placeholder value
                    .keywordAnalysis(List.of("placeholder")) // Placeholder value
                    .needsHumanReview(false) // Placeholder value
                    .build();
        } catch (Exception e) {
            log.error("Error calling AI service for query from user []: {}", request.getQuery(), e);
            throw new AIServiceException("Failed to get response from AI service for query.", e);
        }
    }
}
