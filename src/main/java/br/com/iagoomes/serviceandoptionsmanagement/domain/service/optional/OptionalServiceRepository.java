package br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalServiceRepository extends JpaRepository<OptionalService, Long> {
    boolean existsByNameContains(String name);
}
