package br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionalServiceDTO {
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
}
