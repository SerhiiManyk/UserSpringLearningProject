package dao;

import java.util.List;

public interface CrudDao<E> {

    void save(E entity);

    void update(E entity);

    void delete(E entity);

    E getById(Long id);

    List<E> getAll();
}
