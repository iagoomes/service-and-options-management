package br.com.iagoomes.serviceandoptionsmanagement.service;


import br.com.iagoomes.serviceandoptionsmanagement.controller.mapper.OptionalServiceMapper;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalService;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceRepository;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.impl.OptionalServiceServiceImpl;
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
class OptionalServiceServiceImplTest {
    @InjectMocks
    private OptionalServiceServiceImpl optionalServiceService;
    @Mock
    private OptionalServiceRepository repository;
    @Spy
    private OptionalServiceMapper mapper = Mappers.getMapper(OptionalServiceMapper.class);
    private OptionalServiceDTO almocoDTO;
    private OptionalService almoco;

    @BeforeEach
    void setUp() {
        this.almocoDTO = OptionalServiceDTO.builder()
                .name("Almoço")
                .price(new BigDecimal("80"))
                .build();
        this.almoco = OptionalService.builder()
                .name("Almoço")
                .price(new BigDecimal("80"))
                .build();
    }

    @Test
    @DisplayName("Deve permitir cadastrar o cliente")
    void createCustomerTest() {
        // Arrange
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        when(repository.save(any(OptionalService.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<OptionalServiceDTO> response = optionalServiceService.createService(almocoDTO, uriBuilder);

        // Assert
        assertThat(response.getBody()).isInstanceOf(OptionalServiceDTO.class).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(this.almocoDTO.getName());
        verify(repository, times(1)).save(any(OptionalService.class));
    }

    @Test
    @DisplayName("Deve permitir buscar cliente")
    void findCustomerTest() {
        // Arrange
        Optional<OptionalService> optionalService = Optional.of(almoco);
        when(repository.findById(anyLong())).thenReturn(optionalService);

        // Act
        ResponseEntity<OptionalServiceDTO> response = optionalServiceService.getService(1L);

        // Assert
        assertThat(response.getBody()).isInstanceOf(OptionalServiceDTO.class).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(this.almocoDTO.getName());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Deve permitir listar os clientes")
    void findCustomersTest() {
        // Arrange

        Page<OptionalService> page = new PageImpl<>(Collections.singletonList(almoco));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        ResponseEntity<Page<OptionalServiceDTO>> response = optionalServiceService.getAllServices(Pageable.unpaged());

        // Assert
        assertThat(Objects.requireNonNull(response.getBody()).getContent()).hasSize(1);
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Deve permitir alterar o cliente")
    void updateCustomerTest() {
        // Arrange

        when(repository.getReferenceById(anyLong())).thenReturn(almoco);
        OptionalServiceDTO carlosUpdate = OptionalServiceDTO.builder().name("Almoço prime").build();
        // Act
        ResponseEntity<OptionalServiceDTO> response = optionalServiceService.updateService(1L, carlosUpdate);

        // Assert
        assertThat(response.getBody()).isInstanceOf(OptionalServiceDTO.class).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(carlosUpdate.getName());
        verify(repository, times(1)).getReferenceById(anyLong());
    }

    @Test
    @DisplayName("Deve permitir deletar o cliente")
    void deleteCustomerTest() {
        // Arrange
        when(repository.getReferenceById(anyLong())).thenReturn(almoco);

        // Act
        ResponseEntity<Void> response = optionalServiceService.deleteService(1L);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(repository, times(1)).getReferenceById(anyLong());
    }
}