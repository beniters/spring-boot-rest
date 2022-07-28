package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.exceptions.service.ServiceNotExistException;
import com.ninjaone.backendinterviewproject.domain.Service;
import com.ninjaone.backendinterviewproject.domain.custom.MonthlyCoastReport;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service createServiceEntity(Service service){
        if (serviceRepository.existsServiceByDescriptionAndPlatform(
                service.getDescription(),
                service.getPlatform()
        )){
            throw new ServiceNotExistException();
        }
        return serviceRepository.save(service);
    }

    public void deleteServiceEntity(Long id) {
        if(serviceRepository.existsById(id)){
            serviceRepository.deleteById(id);
            return;
        }

        throw new ServiceNotExistException();
    }

    public List<MonthlyCoastReport> getServiceCoast(){
        return serviceRepository.findAllServicesPerClientNativeQuery();
    }
}
