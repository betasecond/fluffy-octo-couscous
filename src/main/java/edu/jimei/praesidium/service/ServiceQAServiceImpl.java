package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ServiceQADTO;
import edu.jimei.praesidium.entity.ServiceQA;
import edu.jimei.praesidium.repository.ServiceQARepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ServiceQAService.
 */
@Service
@RequiredArgsConstructor
public class ServiceQAServiceImpl implements ServiceQAService {

    private final ServiceQARepository serviceQARepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ServiceQADTO> getAllServiceQAs() {
        List<ServiceQA> serviceQAList = serviceQARepository.findAll();
        return serviceQAList.stream()
                .map(serviceQA -> modelMapper.map(serviceQA, ServiceQADTO.class))
                .collect(Collectors.toList());
    }
} 