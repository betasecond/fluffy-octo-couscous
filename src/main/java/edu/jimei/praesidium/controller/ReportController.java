package edu.jimei.praesidium.controller;

import edu.jimei.praesidium.dto.ApiResponse;
import edu.jimei.praesidium.dto.ReportSection;
import edu.jimei.praesidium.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for serving the main report sections of the application.
 * The data is typically sourced from a static JSON file.
 */
@RestController
@RequestMapping({"/api/v1/reports", "/api/reports"})
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * Retrieves all report sections.
     *
     * @return A response entity containing the list of all report sections.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportSection>>> getAllReports() {
        List<ReportSection> sections = reportService.getAllReportSections();
        return ResponseEntity.ok(ApiResponse.success(sections));
    }
} 