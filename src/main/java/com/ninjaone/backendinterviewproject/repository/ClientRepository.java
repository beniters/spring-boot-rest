package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsClientByName(String name);
}
