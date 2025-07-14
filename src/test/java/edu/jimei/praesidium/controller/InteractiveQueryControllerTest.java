package edu.jimei.praesidium.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jimei.praesidium.BaseControllerTest;
import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;
import edu.jimei.praesidium.service.InteractiveQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InteractiveQueryController.class)
@ActiveProfiles("test")
@DisplayName("InteractiveQueryController")
class InteractiveQueryControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InteractiveQueryService interactiveQueryService;

    @Test
    @DisplayName("POST /api/query - should return a valid response for a valid query")
    void submitQuery_WhenQueryIsValid_ShouldReturnSuccess() throws Exception {
        // Given
        QueryRequest request = new QueryRequest();
        request.setQuery("Tell me a joke");

        QueryResponse serviceResponse = QueryResponse.builder()
                .suggestedAnswer("Why don't scientists trust atoms? Because they make up everything!")
                .confidence(0.99)
                .keywordAnalysis(List.of("joke", "atoms"))
                .needsHumanReview(false)
                .build();
        when(interactiveQueryService.submitQuery(any(QueryRequest.class))).thenReturn(serviceResponse);

        // When & Then
        mockMvc.perform(post("/api/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.suggestedAnswer").value(serviceResponse.getSuggestedAnswer()))
                .andDo(document("interactive-query/submit-query",
                        requestFields(
                                fieldWithPath("query").description("The user's question or query to be processed by the AI.")
                        ),
                        responseFields(
                                fieldWithPath("code").description("The HTTP status code of the response."),
                                fieldWithPath("message").description("A descriptive message for the response status."),
                                fieldWithPath("data.suggestedAnswer").description("The AI-generated suggested answer to the user's query."),
                                fieldWithPath("data.confidence").description("A score representing the AI's confidence in the answer (0.0 to 1.0)."),
                                fieldWithPath("data.keywordAnalysis").description("A list of keywords extracted from the user's query."),
                                fieldWithPath("data.needsHumanReview").description("A boolean flag indicating if the query and answer should be reviewed by a human.")
                        )
                ));
    }

    @Test
    @DisplayName("POST /api/query - should return 400 Bad Request for a blank query")
    void submitQuery_WhenQueryIsBlank_ShouldReturnBadRequest() throws Exception {
        // Given
        QueryRequest request = new QueryRequest();
        request.setQuery(""); // Invalid blank query

        // When & Then
        mockMvc.perform(post("/api/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("query: Query cannot be blank"));
    }
} 