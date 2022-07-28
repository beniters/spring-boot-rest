package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    boolean existsDeviceBySystemName(String systemName);
}
