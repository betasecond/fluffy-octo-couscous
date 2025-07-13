package edu.jimei.praesidium.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for requesting agent assistance.
 * Contains the dialogue history and the agent's current draft.
 *
 * @author Advisor
 */
@Data
public class AgentAssistRequest implements Serializable {

    @NotEmpty
    private List<ChatMessage> dialogueHistory;

    private String currentDraft;

    /**
     * Represents a single message in the dialogue history.
     *
     * @param role    The role of the speaker (e.g., "user", "agent").
     * @param content The content of the message.
     */
    public record ChatMessage(String role, String content) implements Serializable {
    }
} 