package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Platform;
import com.ninjaone.backendinterviewproject.domain.Service;
import com.ninjaone.backendinterviewproject.domain.custom.MonthlyCoastReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    boolean existsServiceByDescriptionAndPlatform(String description, Platform platform);

    @Query(
        value = "SELECT \n" +
                "    c.id as clientId, \n" +
                "    c.name as clientName, \n" +
                "    concat(extract(year from csd.service_date), lpad(extract(month from csd.service_date), 2, 0)) as period, \n" +
                "    sum(coast) as monthlyCoast \n" +
                "FROM\n" +
                "   CLIENT_SERVICE_DEVICE AS csd, \n" +
                "   CLIENT AS c, \n" +
                "   SERVICE AS s \n" +
                "WHERE \n" +
                "   c.ID = csd.CLIENT_ID \n" +
                "   AND s.ID = csd.SERVICE_ID \n" +
                "GROUP BY c.id, c.name, period",
        nativeQuery = true
    )
    List<MonthlyCoastReport> findAllServicesPerClientNativeQuery();
}
