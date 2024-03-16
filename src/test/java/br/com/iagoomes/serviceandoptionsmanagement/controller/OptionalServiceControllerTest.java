package br.com.iagoomes.serviceandoptionsmanagement.controller;


import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalService;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.OptionalServiceDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.optional.impl.OptionalServiceServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class OptionalServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OptionalServiceServiceImpl optionalServiceService;
    private OptionalServiceDTO optionalServiceDTO;
    private OptionalService optionalService;

    @BeforeEach
    void setUp() {
        this.optionalServiceDTO = OptionalServiceDTO.builder()
                .name("Almoço")
                .price(new BigDecimal("50"))
                .build();
        this.optionalService = OptionalService.builder()
                .id(1L)
                .name("Almoço")
                .build();
    }

    @Test
    void createCustomerTest() throws Exception {
        // Arrange
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");
        URI uri = uriBuilder.path("v1/optional_services_option/{id}").buildAndExpand("1").toUri();
        when(optionalServiceService.createService(any(OptionalServiceDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(uri).body(this.optionalServiceDTO));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/optional_services_option")
                        .content(asJsonString(optionalServiceDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(optionalServiceService, times(1))
                .createService(any(OptionalServiceDTO.class), any(UriComponentsBuilder.class));
    }

    @Test
    void findCustomer() throws Exception {
        // Arrange
        when(optionalServiceService.getService(anyLong()))
                .thenReturn(ResponseEntity.ok(this.optionalServiceDTO));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/optional_services_option/{id}", this.optionalService.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(optionalServiceService, times(1))
                .getService(anyLong());
    }

    @Test
    void findCustomers() throws Exception {
        // Arrange
        Page<OptionalServiceDTO> page = new PageImpl<>(Collections.singletonList(optionalServiceDTO));
        when(optionalServiceService.getAllServices(any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(page));

        // Act & Assert
        mockMvc.perform(get("/v1/optional_services_option")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", not(empty())))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void updateCustomer() throws Exception {
        // Arrange
        when(optionalServiceService.updateService(anyLong(), any(OptionalServiceDTO.class)))
                .thenReturn(ResponseEntity.ok(optionalServiceDTO));

        // Act & Assert
        mockMvc.perform(put("/v1/optional_services_option/{id}", this.optionalService.getId())
                        .content(asJsonString(optionalServiceDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        // Arrange
        when(optionalServiceService.deleteService(anyLong()))
                .thenReturn(ResponseEntity.noContent().build());

        // Act & Assert
        mockMvc.perform(delete("/v1/optional_services_option/{id}", this.optionalService.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}