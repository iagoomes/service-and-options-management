package br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface OptionalServiceService {
    ResponseEntity<Page<OptionalServiceDTO>> getAllServices(Pageable page);

    ResponseEntity<OptionalServiceDTO> getService(Long id);

    ResponseEntity<OptionalServiceDTO> createService(OptionalServiceDTO newService, UriComponentsBuilder uriBuilder);

    ResponseEntity<OptionalServiceDTO> updateService(Long id, OptionalServiceDTO updatedService);

    ResponseEntity<Void> deleteService(Long id);
}
