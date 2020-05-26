package com.egolik.taxi.service;

import com.egolik.taxi.dao.GenericDao;
import com.egolik.taxi.dao.OrderAutoDao;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.entity.OrderAuto;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class OrderAutoService implements GenericDao<OrderAuto> {

    private final OrderAutoDao orderAutoDao;

    public void createOrderAuto(int orderId, HashMap<String, Auto> autos){
        orderAutoDao.create(orderId,autos);
    }

    @Override
    public void create(OrderAuto entity) {

    }

    @Override
    public void delete(OrderAuto entity) {

    }

    @Override
    public OrderAuto findById(int id) {
        return null;
    }

    @Override
    public List<OrderAuto> findAll() {
        return null;
    }

    @Override
    public OrderAuto update(OrderAuto entity) {
        return null;
    }
}
