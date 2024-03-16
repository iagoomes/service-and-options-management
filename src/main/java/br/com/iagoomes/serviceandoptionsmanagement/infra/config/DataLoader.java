package br.com.iagoomes.serviceandoptionsmanagement.infra.config;

import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOption;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionRepository;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalService;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final ServiceOptionRepository serviceOptionRepository;
    private final OptionalServiceRepository optionalServiceRepository;

    @Transactional
    public void run(ApplicationArguments args) {
        serviceOptionRepository.save(new ServiceOption(1L, "Café da Manhã", new BigDecimal("65.00")));
        serviceOptionRepository.save(new ServiceOption(2L, "Almoço", new BigDecimal("65.00")));
        serviceOptionRepository.save(new ServiceOption(3L, "Jantar", new BigDecimal("85.00")));
        serviceOptionRepository.save(new ServiceOption(4L, "Massagem Completa", new BigDecimal("250.00")));
        serviceOptionRepository.save(new ServiceOption(5L, "Manicure", new BigDecimal("85.00")));

        optionalServiceRepository.save(new OptionalService(1L, "Refrigerantes", new BigDecimal("9.00")));
        optionalServiceRepository.save(new OptionalService(2L, "Sucos", new BigDecimal("13.00")));
        optionalServiceRepository.save(new OptionalService(3L, "Cervejas Nacionais", new BigDecimal("15.00")));
        optionalServiceRepository.save(new OptionalService(4L, "Cervejas Importadas", new BigDecimal("25.00")));
        optionalServiceRepository.save(new OptionalService(5L, "Caipirinhas", new BigDecimal("35.00")));
    }
}
