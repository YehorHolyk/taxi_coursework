package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.entity.Route;
import com.egolik.taxi.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RouteDao implements GenericDao<Route> {

    private final String INSERT_NEW_ROUTE = "INSERT INTO route (priority,id_address,id_order) " +
            "VALUES (?,?,?);";

    public void create(int orderId, List<Integer> addressesId){

        final Connection connection = DBConfig.getInstance().takeConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ROUTE);
            for(int id : addressesId){
                preparedStatement.setInt(1, addressesId.indexOf(id) + 1);
                preparedStatement.setInt(2, id);
                preparedStatement.setInt(3, orderId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    @Override
    public void create(Route entity) {

    }

    @Override
    public void delete(Route entity) {

    }

    @Override
    public Route findById(int id) {
        return null;
    }

    @Override
    public List<Route> findAll() {
        return null;
    }

    @Override
    public Route update(Route entity) {
        return null;
    }
}
