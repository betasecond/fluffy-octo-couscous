package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the InteractiveQueryService.
 *
 * @author Advisor
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InteractiveQueryServiceImpl implements InteractiveQueryService {

    private final ChatClient chatClient;

    @Override
    public QueryResponse submitQuery(QueryRequest request) {
        log.info("Submitting query to AI: {}", request.getQuery());

        // This is a placeholder for the actual logic which would involve:
        // 1. Searching the vector store for similar documents.
        // 2. Building a rich prompt with the user query and retrieved context.
        // 3. Calling the AI model.
        // 4. Parsing the response and potentially using function calling to get structured data.

        try {
            String response = chatClient.prompt()
                    .user(request.getQuery())
                    .call()
                    .content();

            log.info("Received AI response: {}", response);

            // Placeholder mapping to QueryResponse
            return QueryResponse.builder()
                    .suggestedAnswer(response)
                    .confidence(0.9) // Placeholder value
                    .keywordAnalysis(List.of("placeholder")) // Placeholder value
                    .needsHumanReview(false) // Placeholder value
                    .build();
        } catch (Exception e) {
            log.error("Error calling AI service for query: {}", request.getQuery(), e);
            // In a real scenario, we might throw a custom exception
            // that the GlobalExceptionHandler can handle.
            return QueryResponse.builder()
                    .suggestedAnswer("Sorry, I am unable to process your request at the moment.")
                    .confidence(0.0)
                    .needsHumanReview(true)
                    .build();
        }
    }
} 