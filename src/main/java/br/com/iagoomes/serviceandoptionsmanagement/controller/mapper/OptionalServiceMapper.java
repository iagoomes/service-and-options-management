package br.com.iagoomes.serviceandoptionsmanagement.controller.mapper;

import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalService;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OptionalServiceMapper {
    OptionalServiceDTO optionalServiceToOptionalServiceDTO(OptionalService optionalService);

    @Mapping(target = "id", ignore = true)
    OptionalService optionalServiceDTOToOptionalService(OptionalServiceDTO dto);
}
