package ca.guig.shoe.repository;

import java.util.List;

public interface CrudRepository<T> {

    T save(T entity);

    T read(String id);

    void delete(String id);

    List<T> findAll();
}
