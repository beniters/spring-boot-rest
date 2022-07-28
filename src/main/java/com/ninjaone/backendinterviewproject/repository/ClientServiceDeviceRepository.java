package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.ClientServiceDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientServiceDeviceRepository extends CrudRepository<ClientServiceDevice, Long> {
    boolean existsClientServiceDeviceByClient_IdAndService_IdAndDevice_Id(Long clientId, Long serviceId, Long deviceId);
}
