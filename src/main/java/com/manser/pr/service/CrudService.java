package com.manser.pr.service;

import java.util.List;

public interface CrudService<E> {

    Long save(E entity);

    void update(E entity);

    void delete(E entity);

    E getById(Long id);

    List<E> getAll();
}
