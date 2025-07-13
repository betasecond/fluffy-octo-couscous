package edu.jimei.praesidium.dto.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.jimei.praesidium.dto.ReportSection;
import lombok.Data;

import java.util.List;

/**
 * Helper DTO to parse the root `db.json` file.
 * It's configured to only map the "reports" field and ignore other top-level keys.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DbData {

    @JsonProperty("reports")
    private List<ReportSection> reports;
} 