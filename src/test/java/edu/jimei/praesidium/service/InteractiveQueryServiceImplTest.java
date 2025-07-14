package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.QueryRequest;
import edu.jimei.praesidium.dto.QueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InteractiveQueryServiceImplTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec callResponseSpec;

    @InjectMocks
    private InteractiveQueryServiceImpl interactiveQueryService;

    @BeforeEach
    void setUp() {
        // Mock the fluent API
        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.user(any(String.class))).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponseSpec);
    }

    @Test
    @DisplayName("submitQuery should return a successful response when AI call succeeds")
    void submitQuery_WhenAiCallSucceeds_ShouldReturnSuccessfulResponse() {
        // Given
        QueryRequest request = new QueryRequest();
        request.setQuery("What is the refund policy?");
        String aiResponse = "Our refund policy is 30 days for any unopened products.";
        when(callResponseSpec.content()).thenReturn(aiResponse);

        // When
        QueryResponse queryResponse = interactiveQueryService.submitQuery(request);

        // Then
        assertThat(queryResponse).isNotNull();
        assertThat(queryResponse.getSuggestedAnswer()).isEqualTo(aiResponse);
        assertThat(queryResponse.isNeedsHumanReview()).isFalse();
        assertThat(queryResponse.getConfidence()).isGreaterThan(0.0);
    }

    @Test
    @DisplayName("submitQuery should return an error response when AI call fails")
    void submitQuery_WhenAiCallFails_ShouldReturnErrorResponse() {
        // Given
        QueryRequest request = new QueryRequest();
        request.setQuery("A query that causes an error");
        when(requestSpec.call()).thenThrow(new RuntimeException("AI service unavailable"));

        // When
        QueryResponse queryResponse = interactiveQueryService.submitQuery(request);

        // Then
        assertThat(queryResponse).isNotNull();
        assertThat(queryResponse.getSuggestedAnswer()).contains("unable to process");
        assertThat(queryResponse.isNeedsHumanReview()).isTrue();
        assertThat(queryResponse.getConfidence()).isEqualTo(0.0);
    }
} 