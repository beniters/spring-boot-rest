package com.ninjaone.backendinterviewproject.web.rest;

import com.ninjaone.backendinterviewproject.domain.Client;
import com.ninjaone.backendinterviewproject.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ClientController.CLIENT_URL_PREFIX)
public class ClientController {
    public static final String CLIENT_URL_PREFIX = "/client";
    private final ClientService clientService;

    public ClientController(ClientService service) {
        this.clientService = service;
    }

    @PostMapping
    private ResponseEntity<Client> createClient(@RequestBody Client client){
        Client clientInserted = clientService.createClientEntity(client);
        return ResponseEntity.created(URI.create(String.format("%s/%s", CLIENT_URL_PREFIX, clientInserted.getId())))
                .body(clientInserted);
    }

    @PutMapping
    private ResponseEntity<Client> updateClient(@RequestBody Client client){
        Client clientUpdated = clientService.updateClientEntity(client);
        return ResponseEntity.ok().body(clientUpdated);
    }

    @GetMapping("/{id}")
    private Client getClient(@PathVariable Long id){
        return clientService.getClientEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteClient(@PathVariable Long id){
        clientService.deleteClientEntity(id);
    }
}
