package br.com.iagoomes.serviceandoptionsmanagement.controller;

import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionDTO;
import br.com.iagoomes.serviceandoptionsmanagement.domain.service.option.ServiceOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/services_option", produces = "application/json")
@Tag(name = "services_option")
public class ServiceOptionController {

    private final ServiceOptionService service;

    @GetMapping
    @Operation(summary = "Realiza a busca dos serviços", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A busca dos servicos realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity<Page<ServiceOptionDTO>> getAllServices(Pageable page) {
        return service.getAllServices(page);
    }

    @Operation(summary = "Realiza a busca do serviço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A busca do serviço realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOptionDTO> getService(@PathVariable Long id) {
        return service.getService(id);
    }

    @Operation(summary = "Realiza o cadastro do serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro de serviço realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição contem tributo invalido"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @PostMapping
    public ResponseEntity<ServiceOptionDTO> createService(@RequestBody ServiceOptionDTO newService, UriComponentsBuilder uriBuilder) {
        return service.createService(newService, uriBuilder);
    }

    @Operation(summary = "Realiza a atualização do serviço", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado ou deletado"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOptionDTO> updateService(@PathVariable Long id, @RequestBody ServiceOptionDTO updatedService) {
        return service.updateService(id, updatedService);
    }

    @Operation(summary = "Realiza a atualização do cliente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado ou deletado"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        return service.deleteService(id);
    }
}