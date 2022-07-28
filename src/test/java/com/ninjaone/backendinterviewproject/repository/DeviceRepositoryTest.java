package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Device;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.domain.Platform;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeviceRepositoryTest {
    public static final boolean ACTIVE = true;
    public static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    public static final String SYSTEM_NAME = "Device 1";
    public static final String DEVICE_OTHER = "Device Other";
    public static final String DEVICE_TYPE_NAME = "Device type 100";
    public static final Platform PLATFORM = Platform.ALL;
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DeviceRepository repository;

    @Autowired
    DeviceTypeRepository repositoryChild;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repositoryChild.deleteAll();
    }

    @Test
    public void shouldDeleteAllDevices() {
        entityManager.persist(entity());
        entityManager.persist(entityOther());

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void shouldFindAllDevices() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityOther();
        entityManager.persist(entity2);

        var entity3 = entityThree();
        entityManager.persist(entity3);

        var donations = repository.findAll();
        assertThat(donations).hasSize(3).contains(entity1, entity2, entity3);
    }

    @Test
    public void shouldFindDeviceById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityOther();
        entityManager.persist(entity2);

        var foundDonation = repository.findById(entity2.getId()).get();
        assertThat(foundDonation).isEqualTo(entity2);
    }

    @Test
    public void shouldFindDeviceByName() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        Device entity = Device.builder().systemName(SYSTEM_NAME).build();
        Example<Device> example = Example.of(entity);
        var entityList = repository.findAll(example);
        assertThat(entityList).hasSize(1).contains(entity1);
    }

    @Test
    public void shouldFindNoDevicesIfRepositoryIsEmpty() {
        var donations = repository.findAll();
        assertThat(donations).isEmpty();
    }

    @Test
    public void shouldStoreDevice() {
        var entity = repository.save(entity());
        assertThat(entity).hasFieldOrPropertyWithValue("systemName", SYSTEM_NAME);
        assertThat(entity).hasFieldOrPropertyWithValue("active", ACTIVE);
        assertThat(entity).hasFieldOrPropertyWithValue("createDate", CREATE_DATE);
    }

    @Test
    public void shouldUpdateDeviceById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityOther();
        entityManager.persist(entity2);

        var entity3 = entityThree();
        entityManager.persist(entity3);

        var entity = repository.findById(entity2.getId()).get();
        entity.setSystemName(entity3.getSystemName());
        repository.save(entity);

        var checkEntity = repository.findById(entity2.getId()).get();
        assertThat(checkEntity.getId()).isEqualTo(entity2.getId());
        assertThat(checkEntity.getSystemName()).isEqualTo(entity3.getSystemName());
    }

    @Test
    public void shouldDeleteDeviceById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        repository.deleteById(entity1.getId());

        var donations = repository.findAll();
        assertThat(donations).hasSize(1).contains(entity3);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }
    Device entity(){
        return entity(SYSTEM_NAME);
    }
    Device entityOther(){
        return entity(DEVICE_OTHER);
    }
    Device entityThree(){
        return entity(DEVICE_OTHER + " 3");
    }
    Device entity(String systemName){

        DeviceType child = repositoryChild.save(getChild(systemName));

        return Device.builder()
                .systemName(systemName)
                .active(ACTIVE)
                .createDate(CREATE_DATE)
                .deviceType(child)
            .build();
    }

    private DeviceType getChild(String deviceOther) {
        DeviceType childEntity = DeviceType.builder()
                .name(String.format("Device Type of: %s", deviceOther))
                .platform(PLATFORM)
            .build();
        return childEntity;
    }
}
