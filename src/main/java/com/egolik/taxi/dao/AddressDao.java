package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.entity.Address;
import com.egolik.taxi.exception.AppException;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class AddressDao implements GenericDao<Address> {
    private static final String INSERT_NEW_ADDRESS = "INSERT INTO addresses (district,street,home_number) " +
            "VALUES (?,?,?);";

    public List<Integer> create(TreeMap<Integer,Address> addressMap){

        List<Integer> addressesId = new ArrayList<>();

        final Connection connection = DBConfig.getInstance().takeConnection();


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            for(Map.Entry<Integer,Address> entry : addressMap.entrySet()){
                preparedStatement.setString(1, entry.getValue().getDistrict());
                preparedStatement.setString(2,entry.getValue().getStreet());
                preparedStatement.setString(3,entry.getValue().getHomeNumber());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    addressesId.add((int) generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }

        return addressesId;
    }

    @Override
    public void create(Address entity) {

    }

    @Override
    public void delete(Address entity) {

    }

    @Override
    public Address findById(int id) {
        return null;
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public Address update(Address entity) {
        return null;
    }
}
