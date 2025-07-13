package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;

/**
 * Service for handling interactive queries.
 *
 * @author Advisor
 */
public interface InteractiveQueryService {

    /**
     * Submits a user query and returns an intelligent response.
     *
     * @param request The user's query request.
     * @return A {@link QueryResponse} object containing the suggested answer and analysis.
     */
    QueryResponse submitQuery(QueryRequest request);
} 