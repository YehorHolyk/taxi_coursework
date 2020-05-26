package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.constants.DBConstants;
import com.egolik.taxi.entity.User;
import com.egolik.taxi.entity.UserRole;
import com.egolik.taxi.exception.AppException;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao implements GenericDao<User> {
    private Logger LOG = LoggerFactory.getLogger(UserDao.class);

    private static final String INSERT_NEW_USER = "INSERT INTO user (login,name,id_status,email,password,telephone_number) " +
            "VALUES (?,?,?,?,?,?);";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user;";

    private static final String SELECT_USER_BY_LOGIN_OR_EMAIL = "Select * from user where login = ? or email = ?";

    public Optional<User> findByLoginOrEmail(String login, String email) {
        if (StringUtils.isNullOrEmpty(login) || StringUtils.isNullOrEmpty(email)) {
            return Optional.empty();
        }

        try {
            final Connection connection = DBConfig.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_OR_EMAIL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapToUser(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    public boolean isUserExists(String email){
        if(StringUtils.isNullOrEmpty(email)){
            return false;
        }
        final Connection connection = DBConfig.getInstance().takeConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public User takeUser(String email){
        final Connection connection =DBConfig.getInstance().takeConnection();
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                user.setId(resultSet.getInt("id_user"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setStatus(UserRole.getRoleByStatusCode(resultSet.getInt("id_status")));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setTelephoneNumber(resultSet.getString("telephone_number"));
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return user;
    }

    @Override
    public void create(User entity) {
        try {
            final Connection connection = DBConfig.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getStatus().getStatusCode());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPassword());
            preparedStatement.setString(6, entity.getTelephoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }


    private User mapToUser(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getInt(DBConstants.USER_ID));
            user.setName(resultSet.getString(DBConstants.USER_NAME));
            user.setStatus(UserRole.getRoleByStatusCode(resultSet.getInt(DBConstants.USER_ID_STATUS)));
            user.setLogin(resultSet.getString(DBConstants.USER_LOGIN));
            user.setEmail(resultSet.getString(DBConstants.USER_EMAIL));
            user.setTelephoneNumber(resultSet.getString(DBConstants.USER_TELEPHONE_NUMBER));
            return user;
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }
}
