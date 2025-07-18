package edu.jimei.praesidium.config;

import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.entity.ReviewItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
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

