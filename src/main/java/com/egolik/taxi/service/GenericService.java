package com.egolik.taxi.service;

import java.util.List;

public interface GenericService<T> {
    void create(T entity);
    void delete(T entity);
    T getById(int id);
    List<T> getAll();
    T update(T entity);
}
