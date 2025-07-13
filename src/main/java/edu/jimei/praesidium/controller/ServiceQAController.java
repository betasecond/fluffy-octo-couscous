package edu.jimei.praesidium.controller;

import edu.jimei.praesidium.dto.ServiceQADTO;
import edu.jimei.praesidium.service.ServiceQAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing Service QA data.
 */
@RestController
@RequestMapping({"/api/v1/serviceQA", "/api/serviceQA"})
@RequiredArgsConstructor
public class ServiceQAController {

    private final ServiceQAService serviceQAService;

    /**
     * Handles GET requests to fetch all service QA entries.
     * @return A ResponseEntity containing a list of ServiceQADTOs.
     */
    @GetMapping
    public ResponseEntity<List<ServiceQADTO>> getAllServiceQAs() {
        return ResponseEntity.ok(serviceQAService.getAllServiceQAs());
    }
} 