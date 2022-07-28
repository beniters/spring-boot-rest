package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.domain.Platform;
import com.ninjaone.backendinterviewproject.domain.Service;
import com.ninjaone.backendinterviewproject.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {ServiceService.class})
public class ServiceServiceTest {
    public static final Long ID = 1L;
    public static final Platform PLATFORM = Platform.ALL;
    public static final double COAST = 1D;
    public static final String DESCRIPTION = "Description 1";

    @MockBean
    private ServiceRepository repository;

    @Autowired
    private ServiceService service;

    private Service entity;

    @BeforeEach
    void setup(){
        entity = Service.builder()
                .id(ID)
                .description(DESCRIPTION)
                .coast(COAST)
                .platform(PLATFORM)
            .build();
    }

    @Test
    void saveData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, service.createServiceEntity(entity));
    }

    @Test
    void tryDeleteDataNotExistent(){
        doNothing().when(repository).deleteById(ID);
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.deleteServiceEntity(ID));
        Mockito.verify(repository, times(1)).existsById(ID);
    }

    @Test
    void deleteData(){
        given(repository.findById(anyLong())).willReturn(Optional.of(entity));
        given(repository.save(any(Service.class))).willReturn(entity);
        given(repository.existsById(anyLong())).willReturn(Boolean.TRUE);

        Service savedEntity = service.createServiceEntity(entity);
        assertEquals(entity, savedEntity);

        service.deleteServiceEntity(ID);

        Mockito.verify(repository, times(1)).save(entity);
        Mockito.verify(repository, times(1)).existsById(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}
