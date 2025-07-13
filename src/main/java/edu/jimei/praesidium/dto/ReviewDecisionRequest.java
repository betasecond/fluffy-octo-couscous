package edu.jimei.praesidium.dto;

import edu.jimei.praesidium.enums.ReviewItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDecisionRequest {

    @NotNull(message = "审核状态不能为空")
    private ReviewItemStatus status;

    private String comments;
} 