package br.com.iagoomes.serviceandoptionsmanagement.controller;


import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOption;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionService;
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
class ServiceOptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceOptionService serviceOptionService;
    private ServiceOptionDTO serviceOptionDTO;
    private ServiceOption serviceOption;

    @BeforeEach
    void setUp() {
        this.serviceOptionDTO = ServiceOptionDTO.builder()
                .name("Almoço")
                .pricePerPerson(new BigDecimal("50"))
                .build();
        this.serviceOption = ServiceOption.builder()
                .id(1L)
                .name("Almoço")
                .build();
    }

    @Test
    void createCustomerTest() throws Exception {
        // Arrange
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");
        URI uri = uriBuilder.path("v1/services_option/{id}").buildAndExpand("1").toUri();
        when(serviceOptionService.createService(any(ServiceOptionDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(uri).body(this.serviceOptionDTO));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/services_option")
                        .content(asJsonString(serviceOptionDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(serviceOptionService, times(1))
                .createService(any(ServiceOptionDTO.class), any(UriComponentsBuilder.class));
    }

    @Test
    void findCustomer() throws Exception {
        // Arrange
        when(serviceOptionService.getService(anyLong()))
                .thenReturn(ResponseEntity.ok(this.serviceOptionDTO));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/services_option/{id}", this.serviceOption.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(serviceOptionService, times(1))
                .getService(anyLong());
    }

    @Test
    void findCustomers() throws Exception {
        // Arrange
        Page<ServiceOptionDTO> page = new PageImpl<>(Collections.singletonList(serviceOptionDTO));
        when(serviceOptionService.getAllServices(any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(page));

        // Act & Assert
        mockMvc.perform(get("/v1/services_option")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", not(empty())))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void updateCustomer() throws Exception {
        // Arrange
        when(serviceOptionService.updateService(anyLong(), any(ServiceOptionDTO.class)))
                .thenReturn(ResponseEntity.ok(serviceOptionDTO));

        // Act & Assert
        mockMvc.perform(put("/v1/services_option/{id}", this.serviceOption.getId())
                        .content(asJsonString(serviceOptionDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        // Arrange
        when(serviceOptionService.deleteService(anyLong()))
                .thenReturn(ResponseEntity.noContent().build());

        // Act & Assert
        mockMvc.perform(delete("/v1/services_option/{id}", this.serviceOption.getId())
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