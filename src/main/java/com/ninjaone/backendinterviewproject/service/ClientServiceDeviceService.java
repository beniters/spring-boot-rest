package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.web.rest.dto.ClientServiceDeviceDTO;
import com.ninjaone.backendinterviewproject.repository.ClientRepository;
import com.ninjaone.backendinterviewproject.repository.ClientServiceDeviceRepository;
import com.ninjaone.backendinterviewproject.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.exceptions.client.ClientNotExistException;
import com.ninjaone.backendinterviewproject.exceptions.clientservicedevice.ClientServiceDeviceAlreadyExistException;
import com.ninjaone.backendinterviewproject.exceptions.clientservicedevice.ClientServiceDeviceNotExistException;
import com.ninjaone.backendinterviewproject.exceptions.service.ServiceNotExistException;
import com.ninjaone.backendinterviewproject.domain.ClientServiceDevice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceDeviceService {
    private final ClientServiceDeviceRepository clientServiceDeviceRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final DeviceRepository deviceRepository;

    public ClientServiceDeviceService(ClientServiceDeviceRepository clientServiceDeviceRepository, ClientRepository clientRepository, ServiceRepository serviceRepository, DeviceRepository deviceRepository) {
        this.clientServiceDeviceRepository = clientServiceDeviceRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.deviceRepository = deviceRepository;
    }

    public Optional<ClientServiceDevice> getClientServiceDeviceEntity(Long id){
        Optional<ClientServiceDevice> client = clientServiceDeviceRepository.findById(id);
        if(client.isPresent()){
            return client;
        }
        throw new ClientServiceDeviceNotExistException();
    }

    public ClientServiceDevice createClientServiceDeviceEntity(ClientServiceDeviceDTO clientServiceDevice){
        Long clientId = clientServiceDevice.getClient().getId();
        Long serviceId = clientServiceDevice.getService().getId();
        Long deviceId = clientServiceDevice.getDevice().getId();
        if(clientServiceDeviceRepository.existsClientServiceDeviceByClient_IdAndService_IdAndDevice_Id(
                clientId,
                serviceId,
                deviceId
        )){
            throw new ClientServiceDeviceAlreadyExistException();
        }

        if(!clientRepository.existsById(clientId)){
            throw new ClientNotExistException();
        }

        if(!serviceRepository.existsById(serviceId)){
            throw new ServiceNotExistException();
        }

        if(!deviceRepository.existsById(deviceId)){
            throw new ServiceNotExistException();
        }


        return clientServiceDeviceRepository.save(this.mapper(clientServiceDevice));
    }

    public ClientServiceDevice updateClientServiceDeviceEntity(ClientServiceDevice clientServiceDevice){
        if (!clientServiceDeviceRepository.existsById(clientServiceDevice.getId())) {
            throw new ClientServiceDeviceNotExistException();
        }

        if (clientServiceDeviceRepository.existsClientServiceDeviceByClient_IdAndService_IdAndDevice_Id(
                clientServiceDevice.getClient().getId(),
                clientServiceDevice.getService().getId(),
                clientServiceDevice.getDevice().getId()
        )) {
            throw new ClientServiceDeviceAlreadyExistException();
        }

        return clientServiceDeviceRepository.save(clientServiceDevice);
    }

    public void deleteClientServiceDeviceEntity(Long id) {
        Optional<ClientServiceDevice> clientEntity = getClientServiceDeviceEntity(id);

        if(clientEntity.isPresent()){
            clientServiceDeviceRepository.deleteById(id);
            return;
        }

        throw new ClientServiceDeviceNotExistException();
    }

    private ClientServiceDevice mapper(ClientServiceDeviceDTO serviceProvision){
        return ClientServiceDevice.builder()
                .client(serviceProvision.getClient())
                .service(serviceProvision.getService())
                .device(serviceProvision.getDevice())
                .serviceDate(serviceProvision.getServiceDate())
            .build();
    }


}
