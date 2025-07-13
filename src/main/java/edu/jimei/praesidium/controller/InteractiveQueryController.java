package edu.jimei.praesidium.controller;

import edu.jimei.praesidium.dto.ApiResponse;
import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;
import edu.jimei.praesidium.service.InteractiveQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling interactive queries.
 *
 * @author Advisor
 */
@RestController
@RequestMapping("/api/v1/query")
@RequiredArgsConstructor
public class InteractiveQueryController {

    private final InteractiveQueryService interactiveQueryService;

    /**
     * Submits a user query.
     *
     * @param request the request
     * @return the api response
     */
    @PostMapping
    public ApiResponse<QueryResponse> submitQuery(@Valid @RequestBody QueryRequest request) {
        QueryResponse response = interactiveQueryService.submitQuery(request);
        return ApiResponse.success(response);
    }
} 