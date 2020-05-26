package com.egolik.taxi.service;

import com.egolik.taxi.dao.GenericDao;
import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.dao.UserDao;
import com.egolik.taxi.entity.User;
import com.egolik.taxi.entity.UserRole;
import lombok.AllArgsConstructor;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@AllArgsConstructor
public class UserService implements GenericDao<User> {

    private final UserDao userDao;


    public boolean isExistByLoginOrEmail(String login, String email){
        MongoDBDAO mongoDBDAO = new MongoDBDAO();
        return mongoDBDAO.findByLoginOrEmail(login,email).isPresent();
    }

    public boolean isExistByEmail(String email){
        return userDao.isUserExists(email);
    }

    public User takeUser(String email){
        return userDao.takeUser(email);
    }

    @Override
    public void create(User entity) {
        entity.setStatus(UserRole.USER);
        userDao.create(entity);
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
}
