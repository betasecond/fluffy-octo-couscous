package edu.jimei.praesidium.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jimei.praesidium.dto.AgentAssistRequest;
import edu.jimei.praesidium.dto.AssistResponse;
import edu.jimei.praesidium.exception.AIServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AgentAssistService.
 * This service provides real-time assistance to agents by analyzing conversation context.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AgentAssistServiceImpl implements AgentAssistService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    private static final String SYSTEM_PROMPT = """
            You are a professional assistant for customer service agents.
            Your task is to analyze the provided conversation context (history and current draft) and provide helpful, structured feedback.
            The conversation context will be provided as a JSON object.
            Your response MUST be a valid JSON object that strictly adheres to the structure of the AssistResponse class.
            Do not include any text or explanations outside of the JSON object.

            The AssistResponse structure is as follows:
            {
              "suggestions": ["suggestion 1", "suggestion 2"],
              "complianceAnalysis": {
                "hasIssues": boolean,
                "issues": ["issue 1", "issue 2"]
              },
              "sentimentAnalysis": {
                "type": "POSITIVE | NEGATIVE | NEUTRAL",
                "score": float (from 0.0 to 1.0)
              },
              "informationCompleteness": {
                "isComplete": boolean,
                "missingInfo": ["missing info 1", "missing info 2"]
              }
            }

            json的value 你需要用中文回答
            """;


    @Override
    public AssistResponse getRealtimeAssistance(AgentAssistRequest request) {
        try {
            String requestAsJson = objectMapper.writeValueAsString(request);

            return chatClient.prompt()
                    .system(SYSTEM_PROMPT)
                    .user(requestAsJson)
                    .call()
                    .entity(AssistResponse.class);

        } catch (JsonProcessingException e) {
            log.error("Error serializing AgentAssistRequest to JSON", e);
            throw new AIServiceException("Failed to serialize request for AI service.", e);
        } catch (Exception e) {
            log.error("Error getting real-time assistance for agent : {}", e.getMessage(), e);
            throw new AIServiceException("Failed to get real-time assistance from AI service.", e);
        }
    }
}
