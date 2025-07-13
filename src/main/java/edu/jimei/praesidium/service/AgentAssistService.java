package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.AgentAssistRequest;
import edu.jimei.praesidium.dto.AssistResponse;

/**
 * Service for providing real-time assistance to customer service agents.
 *
 * @author Advisor
 */
public interface AgentAssistService {

    /**
     * Gets real-time assistance based on the conversation context.
     *
     * @param request The agent assistance request containing the dialogue history and current draft.
     * @return An {@link AssistResponse} object with various suggestions and analyses.
     */
    AssistResponse getRealtimeAssistance(AgentAssistRequest request);
} 