package com.manser.pr.dao;

import java.util.List;

public interface CrudDao<E> {

    Long save(E entity);

    void update(E entity);

    void delete(E entity);

    E getById(Long id);

    List<E> getAll();
}
