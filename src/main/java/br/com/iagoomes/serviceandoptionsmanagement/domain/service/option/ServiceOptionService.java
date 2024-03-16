package br.com.iagoomes.serviceandoptionsmanagement.domain.service.option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ServiceOptionService {
    ResponseEntity<Page<ServiceOptionDTO>> getAllServices(Pageable page);

    ResponseEntity<ServiceOptionDTO> getService(Long id);

    ResponseEntity<ServiceOptionDTO> createService(ServiceOptionDTO newService, UriComponentsBuilder uriBuilder);

    ResponseEntity<ServiceOptionDTO> updateService(Long id, ServiceOptionDTO updatedService);

    ResponseEntity<Void> deleteService(Long id);
}
