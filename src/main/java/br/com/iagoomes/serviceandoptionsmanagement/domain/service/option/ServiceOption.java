package br.com.iagoomes.serviceandoptionsmanagement.domain.service.option;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ServiceOption")
@Table(name = "service_option")
public class ServiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal pricePerPerson;

    public void updateInfos(ServiceOptionDTO updatedService) {
        if (updatedService.getName() != null && !updatedService.getName().isBlank()) {
            this.setName(updatedService.getName());
        }
        if (updatedService.getPricePerPerson() != null) {
            this.setPricePerPerson(updatedService.getPricePerPerson());
        }
    }
}
