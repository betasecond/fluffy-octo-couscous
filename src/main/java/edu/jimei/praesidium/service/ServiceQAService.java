package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ServiceQADTO;
import java.util.List;

/**
 * Service interface for managing Service QA data.
 */
public interface ServiceQAService {
    /**
     * Retrieves all Service QA entries.
     * @return A list of all ServiceQADTOs.
     */
    List<ServiceQADTO> getAllServiceQAs();
} 