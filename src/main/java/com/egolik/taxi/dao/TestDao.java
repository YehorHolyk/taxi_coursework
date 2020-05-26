package com.egolik.taxi.dao;

import com.egolik.taxi.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDao {

    private Logger LOG = LoggerFactory.getLogger(TestDao.class);

    private String SELECT_ALL_TABLES = "SHOW TABLES";

    public List<String> test(Connection connection){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_TABLES);

            List<String> testData = new ArrayList<>();
            while (resultSet.next()){
                testData.add(resultSet.getString("Tables_in_taxi"));
            }
            return testData;
        } catch (SQLException e) {
           LOG.error("Test failed", e);

           throw new AppException(e);
        }
    }


}
