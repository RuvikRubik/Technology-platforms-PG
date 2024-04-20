package org.example;


import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class EntityControllerTest {

    @Test
    public void saveEntity_Success() {
        EntityRepository entityRepository = mock(EntityRepository.class);
        EntityController controller = new EntityController(entityRepository);

        String result = controller.saveEntity(1, "test");
        assertEquals("done", result);


    }

    @Test
    public void saveEntity_Duplicate() {
        EntityRepository entityRepository = mock(EntityRepository.class);
        EntityController controller = new EntityController(entityRepository);
        when(entityRepository.existsById(anyInt())).thenReturn(true);
        String result = controller.saveEntity(1, "test");

        assertEquals("bad request", result);
    }

    @Test
    public void findEntityById_Exists() {
        EntityRepository entityRepository = mock(EntityRepository.class);
        EntityController controller = new EntityController(entityRepository);
        when(entityRepository.findById(anyInt())).thenReturn(Optional.of(new Entity(1, "test")));

        String result = controller.findEntityById(1);

        assertEquals("Entity id=1 data=test", result);
    }

    @Test
    public void findEntityById_NotExists() {
        EntityRepository entityRepository = mock(EntityRepository.class);
        EntityController controller = new EntityController(entityRepository);
        when(entityRepository.existsById(anyInt())).thenReturn(false);
        String result = controller.findEntityById(1);

        assertEquals("not found", result);
    }

    @Test
    public void deleteEntity_Exists() {
        EntityRepository entityRepository = mock(EntityRepository.class);
        EntityController controller = new EntityController(entityRepository);
        when(entityRepository.existsById(anyInt())).thenReturn(true);

        String result = controller.deleteEntity(1);

        assertEquals("done", result);
    }

    @Test
    public void deleteEntity_NotExists() {
        EntityRepository entityRepository = mock(EntityRepository.class);
        EntityController controller = new EntityController(entityRepository);
        when(entityRepository.existsById(anyInt())).thenReturn(false);
        String result = controller.deleteEntity(1);

        assertEquals("not found", result);
    }
}