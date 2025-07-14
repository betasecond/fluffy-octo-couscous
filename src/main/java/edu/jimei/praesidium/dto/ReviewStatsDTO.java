package edu.jimei.praesidium.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatsDTO {
    private long totalItems;
    private long pendingCount;
    private long approvedCount;
    private long rejectedCount;
    private long needsInfoCount;
    private Map<String, Long> bySource;
} 