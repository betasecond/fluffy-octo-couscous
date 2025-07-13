package edu.jimei.praesidium.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for submitting a query.
 *
 * @author Advisor
 */
@Data
public class QueryRequest implements Serializable {

    @NotBlank(message = "Query cannot be blank")
    private String query;
} 