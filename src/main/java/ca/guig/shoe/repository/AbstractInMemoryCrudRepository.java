package ca.guig.shoe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractInMemoryCrudRepository<T extends Identifiable> implements CrudRepository<T> {

    private final Map<String, T> data = new HashMap<>();

    @Override
    public T save(T entity) {
        data.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public T read(String id) {
        return data.get(id);
    }

    @Override
    public void delete(String id) {
        data.remove(id);
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(data.values());
    }
}
