package br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.impl;

import br.com.iagoomes.serviceandoptionsmanagement.controller.mapper.ServiceOptionMapper;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOption;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionRepository;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionService;
import br.com.iagoomes.serviceandoptionsmanagement.infra.exceptions.ServiceException;
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
public class ServiceOptionServiceImpl implements ServiceOptionService {

    private final ServiceOptionRepository repository;
    private final ServiceOptionMapper mapper;

    @Override
    public ResponseEntity<Page<ServiceOptionDTO>> getAllServices(Pageable page) {
        Page<ServiceOption> serviceOptionPage = repository.findAll(page);

        List<ServiceOptionDTO> dtos = serviceOptionPage.getContent()
                .stream()
                .map(mapper::serviceOptionToServiceOptionDTO)
                .toList();

        return ResponseEntity.ok(new PageImpl<>(dtos, page, serviceOptionPage.getTotalElements()));
    }

    @Override
    public ResponseEntity<ServiceOptionDTO> getService(Long id) {
        ServiceOption serviceOption = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Service Option not found"));
        ServiceOptionDTO dto = mapper.serviceOptionToServiceOptionDTO(serviceOption);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<ServiceOptionDTO> createService(ServiceOptionDTO newService, UriComponentsBuilder uriBuilder) {
        ServiceOption serviceOption = mapper.serviceOptionDTOToServiceOption(newService);
        ServiceOption serviceOptionSeved = repository.save(serviceOption);
        ServiceOptionDTO response = mapper.serviceOptionToServiceOptionDTO(serviceOptionSeved);
        URI uri = uriBuilder.path("v1/services_option/{id}").buildAndExpand(serviceOptionSeved.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<ServiceOptionDTO> updateService(Long id, ServiceOptionDTO updatedService) {
        ServiceOption serviceOption = repository.getReferenceById(id);
        serviceOption.updateInfos(updatedService);
        ServiceOptionDTO serviceOptionDTO = mapper.serviceOptionToServiceOptionDTO(serviceOption);
        return ResponseEntity.ok(serviceOptionDTO);
    }

    @Override
    public ResponseEntity<Void> deleteService(Long id) {
        ServiceOption serviceOption = repository.getReferenceById(id);
        repository.delete(serviceOption);
        return ResponseEntity.noContent().build();
    }
}
