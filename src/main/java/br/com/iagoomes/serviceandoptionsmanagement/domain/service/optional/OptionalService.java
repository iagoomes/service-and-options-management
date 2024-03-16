package br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional;

import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OptionalService")
@Table(name = "optional_service")
public class OptionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    public void updateInfos(OptionalServiceDTO updateOptionalService) {
        if (updateOptionalService.getName() != null && !updateOptionalService.getName().isBlank()) {
            this.setName(updateOptionalService.getName());
        }
        if (updateOptionalService.getPrice() != null) {
            this.setPrice(updateOptionalService.getPrice());
        }
    }
}
