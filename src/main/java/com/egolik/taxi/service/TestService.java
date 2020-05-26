package com.egolik.taxi.service;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.dao.TestDao;

import java.sql.Connection;
import java.util.List;

public class TestService {

    private final TestDao testDao;
    private DBConfig dbConfig = DBConfig.getInstance();

    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }


    public List<String> getAllTables() {
        Connection connection = dbConfig.takeConnection();

        return testDao.test(connection);
    }

}
