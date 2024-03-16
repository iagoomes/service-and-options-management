package br.com.iagoomes.serviceandoptionsmanagement.domain.service.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOptionRepository extends JpaRepository<ServiceOption, Long> {
}
