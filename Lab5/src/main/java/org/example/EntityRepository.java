package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityRepository {
    private final Map<Integer, Entity> data = new HashMap<>();

    public void save(int id, Entity entity) {
        if (data.containsKey(id)) {
            throw new IllegalArgumentException("Entity with id " + id + " already exists");
        }
        data.put(id, entity);
    }

    public Optional<Entity> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    public void delete(int id) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("Entity with id " + id + " does not exist");
        }
        data.remove(id);
    }

    public boolean existsById(int id) {
        return findById(id).isPresent();
    }
}