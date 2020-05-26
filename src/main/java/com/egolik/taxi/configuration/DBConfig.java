package com.egolik.taxi.configuration;

import com.egolik.taxi.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DBConfig {
    private static final String DB_FILENAME = "database";
    private static final String DB_DRIVER = "driver";
    private static final String DB_URL = "url";
    private static final String DB_LOGIN = "login";
    private static final String DB_PASSWORD = "password";
    private static final String DB_POOL_SIZE = "poolSize";

    private Logger LOG = LoggerFactory.getLogger(DBConfig.class);

    private static DBConfig poolInstance = null;
    private static volatile boolean isCreated = false;

    private BlockingQueue<Connection> connections;

    private String driverName;
    private String password;
    private int poolSize;
    private String login;
    private String url;

    public static DBConfig getInstance() {
        if (!isCreated) {
            synchronized (DBConfig.class) {
                poolInstance = new DBConfig();
                isCreated = true;

            }
        }
        return poolInstance;
    }

    private DBConfig() {
        ResourceBundle bundle = ResourceBundle.getBundle(DB_FILENAME);
        this.poolSize = Integer.parseInt(bundle.getString(DB_POOL_SIZE));
        this.driverName = bundle.getString(DB_DRIVER);
        this.url = bundle.getString(DB_URL);
        this.login = bundle.getString(DB_LOGIN);
        this.password = bundle.getString(DB_PASSWORD);
        initPoolData();
    }

    private void initPoolData() {
        connections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Class.forName(driverName);
                Connection connection = DriverManager.getConnection(url, login, password);
                connections.add(connection);
            } catch (SQLException | ClassNotFoundException e) {
               LOG.error("Unable to initialize connection db", e);
            }
        }
    }

    public Connection takeConnection() {
        Connection connection;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOG.error("Unable to allocate connection", e);
            throw new AppException(e);
        }
        return connection;
    }

    public void dispose() {
        Connection connection;
        while ((connection = connections.poll()) != null) {
            try {
                connection.commit();
                close(connection);
            } catch (SQLException e) {
                LOG.error("Unable to close connection", e);
            }
        }
        LOG.error("Disposing connection pool... ");
    }

    private void close(Connection connection) throws SQLException {
        if (connection.isClosed()) {
            throw new SQLException("Connection has already been closed.");
        }
        if (connection.isReadOnly()) {
            connection.setReadOnly(false);
        }
        if (!connections.offer(connection)) {
            throw new SQLException("Unable to return used connection to the queue of available connections");
        }
    }
}
