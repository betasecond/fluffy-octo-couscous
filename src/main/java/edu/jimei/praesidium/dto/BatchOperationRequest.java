package edu.jimei.praesidium.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class BatchOperationRequest {

    @NotBlank(message = "操作类型不能为空")
    private String operation; // 'approve', 'reject', 'markNeedsInfo'

    @NotEmpty(message = "至少需要一个审核项ID")
    private List<String> itemIds;

    private String comment;
} 