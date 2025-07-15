package edu.jimei.praesidium.config;

import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.entity.ReviewItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Custom mapping for ReviewItem -> ReviewItemDto
        modelMapper.typeMap(ReviewItem.class, ReviewItemDto.class).addMappings(mapper -> {
            mapper.map(ReviewItem::getCreatedAt, ReviewItemDto::setTimestamp);
        });

        return modelMapper;
    }
}
