package com.egolik.taxi.dao;

import com.egolik.taxi.entity.*;

import java.sql.ResultSet;
import java.util.*;

@SuppressWarnings("ALL")
public interface IMyDAO {
    public Optional<User> findByLoginOrEmail(String login,String email);
    public boolean isUserExists(String email);
    public User takeUser(String email);
    public void create(User entity);
    //public User mapToUser(ResultSet resultSet);
    public void updateOrder(int orderId,int statusId);
    public ArrayList<OrderView> takeOrderView(int userId);
    public ArrayList<OrderView> takeAllOrderViews();
    public int create(Order entity, int userId);
    public void create(int orderId, List<Integer> addressesId);
    public void create(int orderId, HashMap<String, Auto> autos);
    public List<Auto> getAllAuto();
    public Auto getByLicensePlate(String licensePlate);
    public void deleteAutoByLicensePlate(String licensePlate);
    public void create(Auto entity);
    public List<Integer> create(TreeMap<Integer,Address> addressMap);
    public List<Auto> findAutoByClassAndPrice(String classAuto, double minPrice, double maxPrice);
}
