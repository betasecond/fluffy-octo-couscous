package edu.jimei.praesidium.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for AI-related beans.
 */
@Configuration
public class AiConfig {

    /**
     * Creates a ChatClient bean using the auto-configured ChatModel.
     * This allows for a centralized and explicit definition of the ChatClient.
     *
     * @param chatModel The ChatModel bean auto-configured by Spring AI.
     * @return A configured ChatClient instance.
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
} 