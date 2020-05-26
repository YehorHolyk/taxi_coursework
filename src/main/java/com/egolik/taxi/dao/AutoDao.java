package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDao implements GenericDao<Auto> {
    private Logger LOG = LoggerFactory.getLogger(AutoDao.class);
    private static final String SELECT_ALL_AUTO = "SELECT * FROM auto order by id_car desc;";
    private static final String SELECT_BY_LICENSE_PLATE = "SELECT * FROM auto WHERE license_plate=?;";
    private static final String DELETE_BY_LICENSE_PLATE = "DELETE FROM auto WHERE license_plate=?";
    private static final String INSERT_AUTO = "INSERT INTO auto(brand,model,license_plate,price,class," +
            "seats,pictureURL,manufacturer_id) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SELECT_MANUFACTURER = "SELECT * FROM manufacturer WHERE manufacturer=?";
    private static final String INSERT_MANUFACTURER = "INSERT INTO manufacturer(manufacturer) VALUES (?)";

    public List<Auto> getAllAuto() {
        final Connection connection = DBConfig.getInstance().takeConnection();
        List<Auto> autoList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTO);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Auto auto = new Auto();
                auto.setBrand(resultSet.getString("brand"));
                auto.setModel(resultSet.getString("model"));
                auto.setLicensePlate(resultSet.getString("license_plate"));
                auto.setPrice(resultSet.getDouble("price"));
                auto.setAutoClass(resultSet.getString("class"));
                auto.setSeats(resultSet.getInt("seats"));
                auto.setPictureURL(resultSet.getString("pictureURL"));
                autoList.add(auto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoList;
    }


    public Auto getByLicensePlate(String licensePlate) {
        final Connection connection = DBConfig.getInstance().takeConnection();
        Auto auto = new Auto();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LICENSE_PLATE);
            preparedStatement.setString(1, licensePlate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                auto.setId(resultSet.getInt("id_car"));
                auto.setBrand(resultSet.getString("brand"));
                auto.setModel(resultSet.getString("model"));
                auto.setLicensePlate(resultSet.getString("license_plate"));
                auto.setPrice(resultSet.getDouble("price"));
                auto.setAutoClass(resultSet.getString("class"));
                auto.setSeats(resultSet.getInt("seats"));
                auto.setPictureURL(resultSet.getString("pictureURL"));
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return auto;
    }

    public void deleteAutoByLicensePlate(String licensePlate) {
        final Connection connection = DBConfig.getInstance().takeConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_LICENSE_PLATE);
            preparedStatement.setString(1, licensePlate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    @Override
    public void create(Auto entity) {
        final Connection connection = DBConfig.getInstance().takeConnection();
        try {
            connection.setAutoCommit(false);
            int manufacturerid = 0;
            PreparedStatement selectStatement = connection.prepareStatement(SELECT_MANUFACTURER);
            selectStatement.setString(1,entity.getManufacturer());
            ResultSet rs = selectStatement.executeQuery();
            if(rs.next()){
                manufacturerid = rs.getInt("id");
            }
            else{
                PreparedStatement insertStatement = connection.prepareStatement(INSERT_MANUFACTURER,Statement.RETURN_GENERATED_KEYS);
                insertStatement.setString(1,entity.getManufacturer());
                insertStatement.executeUpdate();
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    manufacturerid = generatedKeys.getInt(1);
                }
                else {
                    connection.rollback();
                }
            }

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTO);
            preparedStatement.setString(1, entity.getBrand());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setString(3, entity.getLicensePlate());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.setString(5, entity.getAutoClass());
            preparedStatement.setInt(6, entity.getSeats());
            preparedStatement.setString(7, entity.getPictureURL());
            preparedStatement.setInt(8,manufacturerid);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    @Override
    public void delete(Auto entity) {

    }

    @Override
    public Auto findById(int id) {
        return null;
    }

    @Override
    public List<Auto> findAll() {
        return null;
    }

    @Override
    public Auto update(Auto entity) {
        return null;
    }
}
