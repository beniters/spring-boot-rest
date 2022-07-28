package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.repository.ClientRepository;
import com.ninjaone.backendinterviewproject.exceptions.client.ClientAlreadyExistException;
import com.ninjaone.backendinterviewproject.exceptions.client.ClientNotExistException;
import com.ninjaone.backendinterviewproject.domain.Client;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> getClientEntity(Long id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return client;
        }
        throw new ClientNotExistException();
    }

    public Client createClientEntity(Client client){
        if(clientRepository.existsClientByName(client.getName())){
            throw new ClientAlreadyExistException();
        }
        return clientRepository.save(client);
    }

    public Client updateClientEntity(Client client){
        if (!clientRepository.existsById(client.getId())) {
            throw new ClientNotExistException();
        }

        if (clientRepository.existsClientByName(client.getName())) {
            throw new ClientAlreadyExistException();
        }

        return clientRepository.save(client);
    }

    public void deleteClientEntity(Long id) {
        Optional<Client> clientEntity = getClientEntity(id);

        if(clientEntity.isPresent()){
            Client client = clientEntity.get();
            client.setActive(false);
            client.setDeleteDate(ZonedDateTime.now());

            clientRepository.save(client);
            return;
        }

        throw new ClientNotExistException();
    }

}
