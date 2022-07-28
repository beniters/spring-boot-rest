package com.ninjaone.backendinterviewproject.web.rest;

import com.ninjaone.backendinterviewproject.domain.Service;
import com.ninjaone.backendinterviewproject.domain.custom.MonthlyCoastReport;
import com.ninjaone.backendinterviewproject.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ServiceController.SERVICE_URL_PREFIX)
public class ServiceController {
    public static final String SERVICE_URL_PREFIX = "/service";
    private final ServiceService serviceService;

    public ServiceController(ServiceService service) {
        this.serviceService = service;
    }

    @PostMapping
    private ResponseEntity<Service> createService(@RequestBody Service service){
        Service serviceInserted = serviceService.createServiceEntity(service);
        return ResponseEntity.created(URI.create(String.format("%s/%s", SERVICE_URL_PREFIX, serviceInserted.getId())))
                .body(serviceInserted);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteService(@PathVariable Long id){
        serviceService.deleteServiceEntity(id);
    }

    @GetMapping("/report")
    private ResponseEntity<List<MonthlyCoastReport>> getServiceCoast(){
        return ResponseEntity.ok().body(serviceService.getServiceCoast());
    }
}
