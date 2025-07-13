package edu.jimei.praesidium.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for requesting agent assistance.
 * Contains the conversation context and the agent's current draft.
 */
@Data
public class AgentAssistRequest implements Serializable {

    @NotNull
    @Valid
    private Context context;

    private String currentDraft;
    private String assistType;

    /**
     * Represents the context of the conversation.
     */
    @Data
    public static class Context implements Serializable {
        private String sessionId;
        private String agentId;

        @NotEmpty
        private List<DialogueMessage> dialogueHistory;
    }

    /**
     * Represents a single message in the dialogue history.
     */
    @Data
    public static class DialogueMessage implements Serializable {
        private String id;
        private String sender;
        private String content;
        private String timestamp;
        private String type;
    }
} 