package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.AgentAssistRequest;
import edu.jimei.praesidium.dto.AssistResponse;
import edu.jimei.praesidium.exception.AIServiceException;

/**
 * Service interface for providing real-time assistance to customer service agents.
 */
public interface AgentAssistService {

    /**
     * Gets real-time assistance based on the conversation context.
     * This can include compliance checks, sentiment analysis, and information completeness suggestions.
     *
     * @param request The agent assistance request containing the dialogue history and current draft.
     * @return An {@link AssistResponse} object with various suggestions and analyses.
     * @throws AIServiceException if an error occurs while communicating with the AI service.
     */
    AssistResponse getRealtimeAssistance(AgentAssistRequest request);
} 