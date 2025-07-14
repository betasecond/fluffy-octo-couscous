package edu.jimei.praesidium.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewItemResponse extends ReviewItemDto {

    private List<RelatedKnowledgeItemDTO> relatedKnowledgeItems;
    private List<ReviewHistoryDto> reviewHistory;

    @Data
    public static class RelatedKnowledgeItemDTO {
        private String id;
        private String question;
        private double similarityScore;
    }
} 