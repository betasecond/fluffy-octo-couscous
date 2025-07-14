package edu.jimei.praesidium.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jimei.praesidium.BaseControllerTest;
import edu.jimei.praesidium.dto.AgentAssistRequest;
import edu.jimei.praesidium.dto.AssistResponse;
import edu.jimei.praesidium.service.AgentAssistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AgentAssistController.class)
@ActiveProfiles("test")
@DisplayName("AgentAssistController")
class AgentAssistControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AgentAssistService agentAssistService;

    @Test
    @DisplayName("POST /api/agent-assist/suggestions - should return suggestions for a valid request")
    void getSuggestions_WhenRequestIsValid_ShouldReturnSuccess() throws Exception {
        // Given
        var request = new AgentAssistRequest();
        request.setCurrentDraft("I need help with my order.");
        request.setDialogueHistory(List.of(new AgentAssistRequest.ChatMessage("user", "My order is late.")));

        var serviceResponse = AssistResponse.builder()
                .suggestions(List.of(AssistResponse.Suggestion.builder()
                        .type(AssistResponse.SuggestionType.SENTIMENT)
                        .content("Acknowledge the user's frustration. e.g., 'I'm sorry to hear about the delay.'")
                        .build()))
                .complianceAnalysis(AssistResponse.ComplianceAnalysis.builder().hasIssues(false).issues(Collections.emptyList()).build())
                .informationCompleteness(AssistResponse.InformationCompleteness.builder().isComplete(false).missingInfo(List.of("Order ID")).build())
                .sentimentAnalysis(AssistResponse.SentimentAnalysis.builder().type(AssistResponse.SentimentType.NEGATIVE).score(0.8).build())
                .build();
        when(agentAssistService.getRealtimeAssistance(any(AgentAssistRequest.class))).thenReturn(serviceResponse);

        // When & Then
        mockMvc.perform(post("/api/agent-assist/suggestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.suggestions[0].content").value(serviceResponse.getSuggestions().get(0).getContent()))
                .andDo(print()) // Print the request and response
                .andDo(document("agent-assist/get-suggestions",
                        requestFields(
                                fieldWithPath("dialogueHistory").description("A list of previous messages in the conversation."),
                                fieldWithPath("dialogueHistory[].role").description("The role of the speaker (e.g., 'user', 'agent')."),
                                fieldWithPath("dialogueHistory[].content").description("The text content of the message."),
                                fieldWithPath("currentDraft").description("The current text the agent is drafting as a response.")
                        ),
                        responseFields(
                                fieldWithPath("code").description("The HTTP status code."),
                                fieldWithPath("message").description("The response message."),
                                fieldWithPath("data.suggestions").description("A list of suggestions for the agent."),
                                fieldWithPath("data.suggestions[].type").description("The type of suggestion (e.g., 'COMPLIANCE', 'SENTIMENT')."),
                                fieldWithPath("data.suggestions[].content").description("The content of the suggestion."),
                                fieldWithPath("data.complianceAnalysis").description("Analysis of the conversation for compliance issues."),
                                fieldWithPath("data.complianceAnalysis.hasIssues").description("Boolean flag indicating if compliance issues were found."),
                                fieldWithPath("data.complianceAnalysis.issues").description("A list of specific compliance issues found."),
                                fieldWithPath("data.informationCompleteness").description("Analysis of whether all necessary information has been gathered."),
                                fieldWithPath("data.informationCompleteness.isComplete").description("Boolean flag indicating if information is complete."),
                                fieldWithPath("data.informationCompleteness.missingInfo").description("A list of specific pieces of missing information."),
                                fieldWithPath("data.sentimentAnalysis").description("Analysis of the user's sentiment."),
                                fieldWithPath("data.sentimentAnalysis.type").description("The overall sentiment type (e.g., 'POSITIVE', 'NEGATIVE')."),
                                fieldWithPath("data.sentimentAnalysis.score").description("The confidence score of the sentiment analysis (0.0 to 1.0).")
                        )
                ));
    }

    @Test
    @DisplayName("POST /api/agent-assist/suggestions - should return 400 for empty dialogue history")
    void getSuggestions_WhenDialogueHistoryIsEmpty_ShouldReturnBadRequest() throws Exception {
        // Given
        var request = new AgentAssistRequest();
        request.setDialogueHistory(Collections.emptyList()); // Invalid
        request.setCurrentDraft("test");

        // When & Then
        mockMvc.perform(post("/api/agent-assist/suggestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("dialogueHistory: must not be empty"));
    }
} 