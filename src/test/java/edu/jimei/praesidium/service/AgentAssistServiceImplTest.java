package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.AgentAssistRequest;
import edu.jimei.praesidium.dto.AssistResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AgentAssistServiceImplTest {

    @InjectMocks
    private AgentAssistServiceImpl agentAssistService;

    @Test
    @DisplayName("getRealtimeAssistance should return a default response for placeholder implementation")
    void getRealtimeAssistance_Placeholder_ShouldReturnDefaultResponse() {
        // Given
        var request = new AgentAssistRequest();
        request.setCurrentDraft("Some user draft");
        request.setDialogueHistory(Collections.singletonList(
                new AgentAssistRequest.ChatMessage("user", "Hello")
        ));

        // When
        AssistResponse response = agentAssistService.getRealtimeAssistance(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getSuggestions()).isEmpty();
        assertThat(response.getComplianceAnalysis()).isNotNull();
        assertThat(response.getComplianceAnalysis().isHasIssues()).isFalse();
        assertThat(response.getSentimentAnalysis()).isNotNull();
        assertThat(response.getSentimentAnalysis().getType()).isEqualTo(AssistResponse.SentimentType.NEUTRAL);
        assertThat(response.getInformationCompleteness()).isNotNull();
        assertThat(response.getInformationCompleteness().isComplete()).isTrue();
    }
} 