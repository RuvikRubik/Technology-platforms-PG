package org.example;

import java.util.Objects;
import java.util.Optional;

public class EntityController {
    private final EntityRepository entityRepository;

    public EntityController(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public String saveEntity(int id, String data) {
        if (!entityRepository.existsById(id)) {
            Entity entity = new Entity(id, data);
            entityRepository.save(id, entity);
            return "done";
        } else {
            return "bad request";
        }
    }

    public String findEntityById(int id) {
        Optional<Entity> optionalEntity = entityRepository.findById(id);
        return optionalEntity.map(Entity::toString).orElse("not found");
    }

    public String deleteEntity(int id) {
        if (entityRepository.existsById(id)) {
            entityRepository.delete(id);
            return "done";
        } else {
            return "not found";
        }
    }
}
