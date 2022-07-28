package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Client;
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
public class ClientRepositoryTest {
    public static final boolean ACTIVE = true;
    public static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    public static final String NAME = "Client 1";
    public static final String CLIENT_OTHER = "Client Other";
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ClientRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldDeleteAllClients() {
        entityManager.persist(entity());
        entityManager.persist(entity());

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void shouldFindAllClients() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        var donations = repository.findAll();
        assertThat(donations).hasSize(3).contains(entity1, entity2, entity3);
    }

    @Test
    public void shouldFindClientById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var foundDonation = repository.findById(entity2.getId()).get();
        assertThat(foundDonation).isEqualTo(entity2);
    }

    @Test
    public void shouldFindClientByName() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        Client client = Client.builder().name(NAME).build();
        Example<Client> example = Example.of(client);
        var entityList = repository.findAll(example);
        assertThat(entityList).hasSize(2).contains(entity1, entity2);
    }

    @Test
    public void shouldFindNoClientsIfRepositoryIsEmpty() {
        var donations = repository.findAll();
        assertThat(donations).isEmpty();
    }

    @Test
    public void shouldStoreClient() {
        var entity = repository.save(entity());
        assertThat(entity).hasFieldOrPropertyWithValue("name", NAME);
        assertThat(entity).hasFieldOrPropertyWithValue("active", ACTIVE);
        assertThat(entity).hasFieldOrPropertyWithValue("createDate", CREATE_DATE);
    }

    @Test
    public void shouldUpdateClientById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
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
    public void shouldDeleteClientById() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        repository.deleteById(entity2.getId());

        var donations = repository.findAll();
        assertThat(donations).hasSize(2).contains(entity1, entity3);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }
    Client entity(){
        return entity(NAME);
    }
    Client entityOther(){
        return entity(CLIENT_OTHER);
    }
    Client entity(String name){
        return Client.builder()
                .name(name)
                .active(ACTIVE)
                .createDate(CREATE_DATE)
            .build();
    }
}
