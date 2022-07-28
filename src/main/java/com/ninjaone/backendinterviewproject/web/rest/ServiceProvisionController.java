package com.ninjaone.backendinterviewproject.web.rest;

import com.ninjaone.backendinterviewproject.web.rest.dto.ClientServiceDeviceDTO;
import com.ninjaone.backendinterviewproject.domain.ClientServiceDevice;
import com.ninjaone.backendinterviewproject.service.ClientServiceDeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ServiceProvisionController.SERVICE_PROVISION_URL_PREFIX)
public class ServiceProvisionController {
    public static final String SERVICE_PROVISION_URL_PREFIX = "/service-provision";
    private final ClientServiceDeviceService clientServiceDeviceService;

    public ServiceProvisionController(ClientServiceDeviceService clientServiceDeviceService) {
        this.clientServiceDeviceService = clientServiceDeviceService;
    }

    @PostMapping
    private ResponseEntity<ClientServiceDevice> createServiceProvision(@RequestBody ClientServiceDeviceDTO clientServiceDevice){
        ClientServiceDevice serviceProvision =
            clientServiceDeviceService.
                    createClientServiceDeviceEntity(clientServiceDevice);
        return ResponseEntity.created(URI.create(String.format("%s/%s", SERVICE_PROVISION_URL_PREFIX, serviceProvision.getId())))
                .body(serviceProvision);
    }

    @PutMapping
    private ResponseEntity<ClientServiceDevice> updateServiceProvision(@RequestBody ClientServiceDevice clientServiceDevice){
        ClientServiceDevice serviceProvisionUpdated = clientServiceDeviceService.updateClientServiceDeviceEntity(clientServiceDevice);
        return ResponseEntity.ok().body(serviceProvisionUpdated);
    }

    @GetMapping("/{id}")
    private ClientServiceDevice getServiceProvision(@PathVariable Long id){
        return clientServiceDeviceService.getClientServiceDeviceEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteServiceProvision(@PathVariable Long id){
        clientServiceDeviceService.deleteClientServiceDeviceEntity(id);
    }
}
