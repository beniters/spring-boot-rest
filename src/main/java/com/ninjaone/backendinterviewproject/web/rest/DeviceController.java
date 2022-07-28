package com.ninjaone.backendinterviewproject.web.rest;

import com.ninjaone.backendinterviewproject.domain.Device;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(DeviceController.DEVICE_URL_PREFIX)
public class DeviceController {
    public static final String DEVICE_URL_PREFIX = "/device";
    private final DeviceService deviceService;

    public DeviceController(DeviceService service) {
        this.deviceService = service;
    }

    @PostMapping
    private ResponseEntity<Device> createDevice(@RequestBody Device device){
        Device deviceInserted = deviceService.createDeviceEntity(device);
        return ResponseEntity.created(URI.create(String.format("%s/%s", DEVICE_URL_PREFIX, deviceInserted.getId())))
                .body(deviceInserted);
    }

    @PutMapping
    private ResponseEntity<Device> updateDevice(@RequestBody Device device){
        Device deviceUpdated = deviceService.updateDeviceEntity(device);
        return ResponseEntity.ok().body(deviceUpdated);
    }

    @GetMapping("/{id}")
    private Device getDevice(@PathVariable Long id){
        return deviceService.getDeviceEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteDevice(@PathVariable Long id){
        deviceService.deleteDeviceEntity(id);
    }
}
