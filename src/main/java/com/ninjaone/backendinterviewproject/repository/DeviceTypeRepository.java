package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    boolean existsDeviceByName(String name);
}
