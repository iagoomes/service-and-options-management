package br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.impl;

import br.com.iagoomes.serviceandoptionsmanagement.controller.mapper.OptionalServiceMapper;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalService;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceRepository;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceService;
import br.com.iagoomes.serviceandoptionsmanagement.infra.exceptions.ServiceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionalServiceServiceImpl implements OptionalServiceService {

    private final OptionalServiceRepository repository;
    private final OptionalServiceMapper mapper;

    @Override
    public ResponseEntity<Page<OptionalServiceDTO>> getAllServices(Pageable page) {
        Page<OptionalService> optionalServices = repository.findAll(page);

        List<OptionalServiceDTO> dtos = optionalServices.getContent()
                .stream()
                .map(mapper::optionalServiceToOptionalServiceDTO)
                .toList();

        return ResponseEntity.ok(new PageImpl<>(dtos, page, optionalServices.getTotalElements()));
    }

    @Override
    public ResponseEntity<OptionalServiceDTO> getService(Long id) {
        OptionalService serviceOption = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Service Option not found"));
        OptionalServiceDTO dto = mapper.optionalServiceToOptionalServiceDTO(serviceOption);
        return ResponseEntity.ok(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<OptionalServiceDTO> createService(OptionalServiceDTO newService, UriComponentsBuilder uriBuilder) {
        repository.existsByNameContains(newService.getName().trim());
        OptionalService optionalService = mapper.optionalServiceDTOToOptionalService(newService);
        OptionalService serviceOptionSeved = repository.save(optionalService);
        OptionalServiceDTO response = mapper.optionalServiceToOptionalServiceDTO(serviceOptionSeved);
        URI uri = uriBuilder.path("v1/optional_services_option/{id}").buildAndExpand(serviceOptionSeved.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @Transactional
    public ResponseEntity<OptionalServiceDTO> updateService(Long id, OptionalServiceDTO updatedOptionalService) {
        OptionalService optionalService = repository.getReferenceById(id);
        optionalService.updateInfos(updatedOptionalService);
        OptionalServiceDTO optionalServiceDTO = mapper.optionalServiceToOptionalServiceDTO(optionalService);
        return ResponseEntity.ok(optionalServiceDTO);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteService(Long id) {
        OptionalService optionalService = repository.getReferenceById(id);
        repository.delete(optionalService);
        return ResponseEntity.noContent().build();
    }
}
