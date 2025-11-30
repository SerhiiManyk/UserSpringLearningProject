package com.manser.pr.service;

import java.util.List;

public interface CrudService<E> {

    void save(E entity);

    void update(E entity);

    void delete(E entity);

    E getById(int id);

    List<E> getAll();
}
