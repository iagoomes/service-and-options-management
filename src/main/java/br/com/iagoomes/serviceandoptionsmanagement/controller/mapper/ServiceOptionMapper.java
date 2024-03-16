package br.com.iagoomes.serviceandoptionsmanagement.controller.mapper;

import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOption;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceOptionMapper {
    ServiceOptionDTO serviceOptionToServiceOptionDTO(ServiceOption serviceOption);
    @Mapping(target = "id", ignore = true)
    ServiceOption serviceOptionDTOToServiceOption(ServiceOptionDTO dto);
}
