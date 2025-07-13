package edu.jimei.praesidium.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReviewDecisionRequest {
    @NotBlank(message = "审核项ID不能为空")
    private String itemId;

    @NotBlank(message = "审核决定不能为空")
    private String decision; // 'approve', 'reject', 'needsInfo'

    private String comment;

    private String standardQuestion;

    private String suggestedAnswer;

    private MetadataDTO metadata;
} 