package com.egolik.taxi.dao;

import java.util.List;

public interface GenericDao<T> {
    void create(T entity);
    void delete(T entity);
    T findById(int id);
    List<T> findAll();
    T update(T entity);
}
