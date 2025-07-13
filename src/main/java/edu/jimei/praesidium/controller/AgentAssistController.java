package edu.jimei.praesidium.controller;

import edu.jimei.praesidium.dto.AgentAssistRequest;
import edu.jimei.praesidium.dto.ApiResponse;
import edu.jimei.praesidium.dto.AssistResponse;
import edu.jimei.praesidium.service.AgentAssistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling agent assistance requests.
 *
 * @author Advisor
 */
@RestController
@RequestMapping({"/api/v1/agent-assist", "/api/agent-assist"})
@RequiredArgsConstructor
public class AgentAssistController {

    private final AgentAssistService agentAssistService;

    /**
     * Gets real-time assistance suggestions.
     *
     * @param request the request
     * @return the api response
     */
    @PostMapping("/suggestions")
    public ApiResponse<AssistResponse> getSuggestions(@Valid @RequestBody AgentAssistRequest request) {
        AssistResponse response = agentAssistService.getRealtimeAssistance(request);
        return ApiResponse.success(response);
    }
} 