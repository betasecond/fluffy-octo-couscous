package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ReportSection;

import java.util.List;

/**
 * Service interface for retrieving report data.
 */
public interface ReportService {

    /**
     * Retrieves all report sections.
     *
     * @return A list of {@link ReportSection} objects.
     */
    List<ReportSection> getAllReportSections();
} 