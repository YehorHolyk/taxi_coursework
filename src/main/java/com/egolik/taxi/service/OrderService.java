package com.egolik.taxi.service;

import com.egolik.taxi.dao.GenericDao;
import com.egolik.taxi.dao.OrderDao;
import com.egolik.taxi.entity.Order;
import com.egolik.taxi.entity.OrderView;
import com.egolik.taxi.entity.Zapros;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OrderService implements GenericDao<Order> {

    private final OrderDao orderDao;

    public int createOrder(Order order, int userId){
        return orderDao.create(order,userId);
    }

    public ArrayList<OrderView> takeOrderView(int userId){
        return orderDao.takeOrderView(userId);
    }

    public ArrayList<Zapros> takeFavor(){
        return orderDao.takeFavor();
    }

    public ArrayList<OrderView> takeAllOrderViews(){
        return orderDao.takeAllOrderViews();
    }

    public void updateOrderStatus(int orderId,int statusId){
        orderDao.updateOrder(orderId,statusId);
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
