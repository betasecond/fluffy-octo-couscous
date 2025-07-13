package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;
import edu.jimei.praesidium.exception.AIServiceException;

/**
 * Service interface for handling interactive user queries.
 */
public interface InteractiveQueryService {

    /**
     * Submits a user query and returns an intelligent response.
     *
     * @param request The user's query request, containing the query string and user ID.
     * @return A {@link QueryResponse} object containing the suggested answer and analysis.
     * @throws AIServiceException if an error occurs while communicating with the AI service.
     */
    QueryResponse submitQuery(QueryRequest request);
} 