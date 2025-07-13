package edu.jimei.praesidium.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jimei.praesidium.dto.ReportSection;
import edu.jimei.praesidium.dto.internal.DbData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Override
    public List<ReportSection> getAllReportSections() {
        try {
            Resource resource = resourceLoader.getResource("classpath:db.json");
            InputStream inputStream = resource.getInputStream();
            DbData data = objectMapper.readValue(inputStream, DbData.class);
            return data.getReports() != null ? data.getReports() : Collections.emptyList();
        } catch (Exception e) {
            log.error("Failed to read or parse db.json for reports. Make sure the file exists in src/main/resources and is valid JSON.", e);
            return Collections.emptyList();
        }
    }
} 