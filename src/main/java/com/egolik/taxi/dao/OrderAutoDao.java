package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.entity.OrderAuto;
import com.egolik.taxi.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAutoDao implements GenericDao<OrderAuto> {

    private final String INSERT_ORDER_AUTO = "INSERT INTO order_auto (id_order,id_car) " +
            "VALUES (?,?);";

    public void create(int orderId, HashMap<String, Auto> autos){

        final Connection connection = DBConfig.getInstance().takeConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_AUTO);
            for(Map.Entry<String,Auto> entry : autos.entrySet()){
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2,entry.getValue().getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
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
