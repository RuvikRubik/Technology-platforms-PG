package org.example;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


public class EntityRepositoryTest {

    @Test
    public void saveAndFindEntityById() {
        EntityRepository repository = new EntityRepository();
        Entity entity = new Entity(1, "test");
        repository.save(1, entity);
        Optional<Entity> result = repository.findById(1);
        assertEquals(entity, result.get());
    }

    @Test
    public void deleteEntity() {
        EntityRepository repository = new EntityRepository();
        Entity entity = new Entity(1, "test");
        repository.save(1, entity);
        repository.delete(1);
        Optional<Entity> result = repository.findById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void saveDuplicateEntity() {
        EntityRepository repository = new EntityRepository();
        Entity entity = new Entity(1, "test");
        repository.save(1, entity);
        assertThrows(IllegalArgumentException.class, () -> repository.save(1, entity));
    }

    @Test
    public void findExistingEntity() {
        EntityRepository repository = new EntityRepository();
        Entity entity = new Entity(1, "test");
        repository.save(1, entity);
        Optional<Entity> result = repository.findById(1);
        assertTrue(result.isPresent());
    }

    @Test
    public void findNonExistingEntity() {
        EntityRepository repository = new EntityRepository();
        Optional<Entity> result = repository.findById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteNonExistingEntity() {
        EntityRepository repository = new EntityRepository();
        assertThrows(IllegalArgumentException.class, () -> repository.delete(1));
    }
}