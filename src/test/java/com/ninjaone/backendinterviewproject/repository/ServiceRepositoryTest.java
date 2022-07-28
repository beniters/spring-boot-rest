package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Service;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceRepositoryTest {
    public static final String DESCRIPTION = "Description One";
    public static final String SERVICE_TWO = "Service Two";
    public static final String SERVICE_OTHER = "Service Other";
    public static final Platform PLATFORM = Platform.ALL;
    public static final double COAST = 1D;
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ServiceRepository repository;

    @Autowired
    DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void shouldDeleteAllServices() {
        entityManager.persist(entity());
        entityManager.persist(entityTwo());

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void shouldFindAllServices() {
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
    public void shouldFindServiceById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var founded = repository.findById(entity2.getId()).get();
        assertThat(founded).isEqualTo(entity2);
    }

    @Test
    public void shouldFindServiceByDescription() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        Service client = Service.builder().description(DESCRIPTION).build();
        Example<Service> example = Example.of(client);
        var entityList = repository.findAll(example);
        assertThat(entityList).hasSize(1).contains(entity1);
    }

    @Test
    public void shouldFindNoServicesIfRepositoryIsEmpty() {
        var founded = repository.findAll();
        assertThat(founded).isEmpty();
    }

    @Test
    public void shouldStoreService() {
        var entity = repository.save(entity());
        assertThat(entity).hasFieldOrPropertyWithValue("description", DESCRIPTION);
        assertThat(entity).hasFieldOrPropertyWithValue("coast", COAST);
        assertThat(entity).hasFieldOrPropertyWithValue("platform", PLATFORM);
    }

    @Test
    public void shouldUpdateServiceById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entityTwo();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        var entity = repository.findById(entity2.getId()).get();
        entity.setDescription(entity3.getDescription());
        repository.save(entity);

        var checkEntity = repository.findById(entity2.getId()).get();
        assertThat(checkEntity.getId()).isEqualTo(entity2.getId());
        assertThat(checkEntity.getDescription()).isEqualTo(entity3.getDescription());
    }

    @Test
    public void shouldDeleteServiceById() {
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
    Service entity(){
        return entity(DESCRIPTION);
    }
    Service entityOther(){
        return entity(SERVICE_OTHER);
    }
    Service entityTwo(){
        return entity(SERVICE_TWO);
    }
    Service entity(String description){
        return Service.builder()
                .description(description)
                .coast(COAST)
                .platform(PLATFORM)
            .build();
    }
}
