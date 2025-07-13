package edu.jimei.praesidium.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web-related configuration for the application.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) for the application.
     * This allows web clients from any origin to interact with the API, which is
     * essential for modern web applications where the frontend and backend are
     * served from different domains.
     *
     * @param registry the CorsRegistry to which the CORS configuration is added.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("*") // Allow all origins
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Allow all standard methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(false); // Credentials not allowed with wildcard origin
    }
} 