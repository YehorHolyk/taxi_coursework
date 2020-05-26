package com.egolik.taxi.service;

import com.egolik.taxi.dao.GenericDao;
import com.egolik.taxi.dao.RouteDao;
import com.egolik.taxi.entity.Order;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RouteService implements GenericDao<Order> {

    private final RouteDao routeDao;

    public void createRoute(int orderId, List<Integer> addressesId){
        routeDao.create(orderId,addressesId);
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public void delete(Order entity) {

    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order entity) {
        return null;
    }
}
