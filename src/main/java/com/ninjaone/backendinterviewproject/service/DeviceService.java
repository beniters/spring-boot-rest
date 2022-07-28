package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.exceptions.device.DeviceAlreadyExistException;
import com.ninjaone.backendinterviewproject.exceptions.device.DeviceNotExistException;
import com.ninjaone.backendinterviewproject.domain.Device;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Optional<Device> getDeviceEntity(Long id){
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){
            return device;
        }
        throw new DeviceNotExistException();
    }

    public Device createDeviceEntity(Device device){
        if(deviceRepository.existsDeviceBySystemName(device.getSystemName())){
            throw new DeviceAlreadyExistException();
        }
        return deviceRepository.save(device);
    }

    public Device updateDeviceEntity(Device device){
        if (!deviceRepository.existsById(device.getId())) {
            throw new DeviceNotExistException();
        }

        if (deviceRepository.existsDeviceBySystemName(device.getSystemName())) {
            throw new DeviceAlreadyExistException();
        }

        return deviceRepository.save(device);
    }

    public void deleteDeviceEntity(Long id) {
        Optional<Device> deviceEntity = getDeviceEntity(id);

        if(deviceEntity.isPresent()){
            Device device = deviceEntity.get();
            device.setActive(false);
            device.setDeleteDate(ZonedDateTime.now());

            deviceRepository.save(device);
            return;
        }

        throw new DeviceNotExistException();
    }

}
