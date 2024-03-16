package br.com.iagoomes.serviceandoptionsmanagement.service;


import br.com.iagoomes.serviceandoptionsmanagement.controller.mapper.ServiceOptionMapper;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOption;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionRepository;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.impl.ServiceOptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceOptionServiceImplTest {
    @InjectMocks
    private ServiceOptionServiceImpl serviceOptionService;
    @Mock
    private ServiceOptionRepository repository;
    @Spy
    private ServiceOptionMapper mapper = Mappers.getMapper(ServiceOptionMapper.class);
    private ServiceOptionDTO almocoDTO;
    private ServiceOption almoco;

    @BeforeEach
    void setUp() {
        this.almocoDTO = ServiceOptionDTO.builder()
                .name("Almoço")
                .pricePerPerson(new BigDecimal("80"))
                .build();
        this.almoco = ServiceOption.builder()
                .name("Almoço")
                .pricePerPerson(new BigDecimal("80"))
                .build();
    }

    @Test
    @DisplayName("Deve permitir cadastrar o cliente")
    void createCustomerTest() {
        // Arrange
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        when(repository.save(any(ServiceOption.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<ServiceOptionDTO> response = serviceOptionService.createService(almocoDTO, uriBuilder);

        // Assert
        assertThat(response.getBody()).isInstanceOf(ServiceOptionDTO.class).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(this.almocoDTO.getName());
        verify(repository, times(1)).save(any(ServiceOption.class));
    }

    @Test
    @DisplayName("Deve permitir buscar cliente")
    void findCustomerTest() {
        // Arrange
        Optional<ServiceOption> ServiceOption = Optional.of(almoco);
        when(repository.findById(anyLong())).thenReturn(ServiceOption);

        // Act
        ResponseEntity<ServiceOptionDTO> response = serviceOptionService.getService(1L);

        // Assert
        assertThat(response.getBody()).isInstanceOf(ServiceOptionDTO.class).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(this.almocoDTO.getName());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Deve permitir listar os clientes")
    void findCustomersTest() {
        // Arrange

        Page<ServiceOption> page = new PageImpl<>(Collections.singletonList(almoco));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        ResponseEntity<Page<ServiceOptionDTO>> response = serviceOptionService.getAllServices(Pageable.unpaged());

        // Assert
        assertThat(Objects.requireNonNull(response.getBody()).getContent()).hasSize(1);
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Deve permitir alterar o cliente")
    void updateCustomerTest() {
        // Arrange

        when(repository.getReferenceById(anyLong())).thenReturn(almoco);
        ServiceOptionDTO carlosUpdate = ServiceOptionDTO.builder().name("Almoço prime").build();
        // Act
        ResponseEntity<ServiceOptionDTO> response = serviceOptionService.updateService(1L, carlosUpdate);

        // Assert
        assertThat(response.getBody()).isInstanceOf(ServiceOptionDTO.class).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(carlosUpdate.getName());
        verify(repository, times(1)).getReferenceById(anyLong());
    }

    @Test
    @DisplayName("Deve permitir deletar o cliente")
    void deleteCustomerTest() {
        // Arrange
        when(repository.getReferenceById(anyLong())).thenReturn(almoco);

        // Act
        ResponseEntity<Void> response = serviceOptionService.deleteService(1L);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(repository, times(1)).getReferenceById(anyLong());
    }
}