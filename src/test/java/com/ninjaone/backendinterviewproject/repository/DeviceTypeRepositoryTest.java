package com.ninjaone.backendinterviewproject.repository;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeviceTypeRepositoryTest {
    public static final String NAME = "DeviceType 1";
    public static final String DEVICE_TYPE_TWO = "DeviceType Two";
    public static final String DEVICE_TYPE_OTHER = "DeviceType Other";
    public static final Platform PLATFORM = Platform.ALL;
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DeviceTypeRepository repository;

    @Autowired
    DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void shouldDeleteAllDeviceTypes() {
        entityManager.persist(entity());
        entityManager.persist(entityTwo());

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void shouldFindAllDeviceTypes() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        var founded = repository.findAll();
        assertThat(founded).hasSize(3).contains(entity1, entity2, entity3);
    }

    @Test
    public void shouldFindDeviceTypeById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var founded = repository.findById(entity2.getId()).get();
        assertThat(founded).isEqualTo(entity2);
    }

    @Test
    public void shouldFindDeviceTypeByName() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        DeviceType client = DeviceType.builder().name(NAME).build();
        Example<DeviceType> example = Example.of(client);
        var entityList = repository.findAll(example);
        assertThat(entityList).hasSize(1).contains(entity1);
    }

    @Test
    public void shouldFindNoDeviceTypesIfRepositoryIsEmpty() {
        var founded = repository.findAll();
        assertThat(founded).isEmpty();
    }

    @Test
    public void shouldStoreDeviceType() {
        var entity = repository.save(entity());
        assertThat(entity).hasFieldOrPropertyWithValue("name", NAME);
        assertThat(entity).hasFieldOrPropertyWithValue("platform", PLATFORM);
    }

    @Test
    public void shouldUpdateDeviceTypeById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        var entity = repository.findById(entity2.getId()).get();
        entity.setName(entity3.getName());
        repository.save(entity);

        var checkEntity = repository.findById(entity2.getId()).get();
        assertThat(checkEntity.getId()).isEqualTo(entity2.getId());
        assertThat(checkEntity.getName()).isEqualTo(entity3.getName());
    }

    @Test
    public void shouldDeleteDeviceTypeById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        repository.deleteById(entity2.getId());

        var founded = repository.findAll();
        assertThat(founded).hasSize(2).contains(entity1, entity3);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }
    DeviceType entity(){
        return entity(NAME);
    }
    DeviceType entityOther(){
        return entity(DEVICE_TYPE_OTHER);
    }
    DeviceType entityTwo(){
        return entity(DEVICE_TYPE_TWO);
    }
    DeviceType entity(String name){
        return DeviceType.builder()
                .name(name)
                .platform(PLATFORM)
            .build();
    }
}
