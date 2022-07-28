package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.domain.Device;
import com.ninjaone.backendinterviewproject.repository.DeviceRepository;
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

@SpringJUnitConfig(classes = {DeviceService.class})
public class DeviceServiceTest {
    public static final Long ID = 1L;
    public static final boolean ACTIVE = true;
    public static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    public static final String SYSTEM_NAME = "Device 1";

    @MockBean
    private DeviceRepository repository;

    @Autowired
    private DeviceService service;

    private Device entity;

    @BeforeEach
    void setup(){
        entity = Device.builder()
                .id(ID)
                .systemName(SYSTEM_NAME)
                .active(ACTIVE)
                .createDate(CREATE_DATE)
            .build();
    }

    @Test
    void tryGetDataNotExistent(){
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.getDeviceEntity(ID));
        Mockito.verify(repository, times(1)).findById(ID);
    }

    @Test
    void getData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<Device> entityOptional = service.getDeviceEntity(ID);

        Device actualEntity = entityOptional.orElse(null);
        assert actualEntity != null;

        assertThat(entity.getId()).isEqualTo(actualEntity.getId());
        assertThat(entity.getSystemName()).isEqualTo(actualEntity.getSystemName());
        assertThat(entity.getActive()).isEqualTo(actualEntity.getActive());
        assertThat(entity.getCreateDate()).isEqualTo(actualEntity.getCreateDate());
    }

    @Test
    void saveData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, service.createDeviceEntity(entity));
    }

    @Test
    void tryUpdateDataNotExistent() {
        when(repository.existsById(ID)).thenReturn(Boolean.FALSE);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.updateDeviceEntity(entity));
        Mockito.verify(repository, times(1)).existsById(ID);

    }

    @Test
    void tryDeleteDataNotExistent(){
        doNothing().when(repository).deleteById(ID);
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> service.deleteDeviceEntity(ID));
        Mockito.verify(repository, times(1)).findById(ID);
    }

    @Test
    void deleteData(){
        given(repository.findById(anyLong())).willReturn(Optional.of(entity));
        given(repository.save(any(Device.class))).willReturn(entity);

        Device savedEntity = service.createDeviceEntity(entity);
        assertEquals(entity, savedEntity);

        service.deleteDeviceEntity(ID);
        Mockito.verify(repository, times(2)).save(entity);
    }
}
