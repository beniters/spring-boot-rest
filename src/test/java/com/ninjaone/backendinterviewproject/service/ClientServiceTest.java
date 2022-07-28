package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.domain.Client;
import com.ninjaone.backendinterviewproject.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {ClientService.class})
public class ClientServiceTest {
    public static final Long ID = 1L;
    public static final boolean ACTIVE = true;
    public static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    public static final String NAME = "Client 1";

    @MockBean
    private ClientRepository repository;

    @Autowired
    private ClientService service;

    private Client entity;

    @BeforeEach
    void setup(){
        entity = Client.builder()
                .id(ID)
                .name(NAME)
                .active(ACTIVE)
                .createDate(CREATE_DATE)
            .build();
    }

    @Test
    void tryGetDataNotExistent(){
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.getClientEntity(ID));
        Mockito.verify(repository, times(1)).findById(ID);
    }

    @Test
    void getData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<Client> entityOptional = service.getClientEntity(ID);

        Client actualEntity = entityOptional.orElse(null);
        assert actualEntity != null;

        assertThat(entity.getId()).isEqualTo(actualEntity.getId());
        assertThat(entity.getName()).isEqualTo(actualEntity.getName());
        assertThat(entity.getActive()).isEqualTo(actualEntity.getActive());
        assertThat(entity.getCreateDate()).isEqualTo(actualEntity.getCreateDate());
    }

    @Test
    void saveData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, service.createClientEntity(entity));
    }

    @Test
    void tryUpdateDataNotExistent() {
        when(repository.existsById(ID)).thenReturn(Boolean.FALSE);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.updateClientEntity(entity));
        Mockito.verify(repository, times(1)).existsById(ID);

    }

    @Test
    void tryDeleteDataNotExistent(){
        doNothing().when(repository).deleteById(ID);
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.deleteClientEntity(ID));
        Mockito.verify(repository, times(1)).findById(ID);
    }

    @Test
    void deleteData(){
        given(repository.findById(anyLong())).willReturn(Optional.of(entity));
        given(repository.save(any(Client.class))).willReturn(entity);

        Client savedEntity = service.createClientEntity(entity);
        assertEquals(entity, savedEntity);

        service.deleteClientEntity(ID);
        Mockito.verify(repository, times(2)).save(entity);
    }
}
