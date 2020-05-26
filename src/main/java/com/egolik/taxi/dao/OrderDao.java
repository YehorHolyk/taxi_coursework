package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.entity.Order;
import com.egolik.taxi.entity.OrderStatus;
import com.egolik.taxi.entity.OrderView;
import com.egolik.taxi.entity.Zapros;
import com.egolik.taxi.exception.AppException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements GenericDao<Order> {

    private static final String INSERT_NEW_ORDER = "INSERT INTO `order` (sum,ordering_date,id_status,id_user) " +
            "VALUES (?,?,?,?);";

    private static final String GET_ORDER_VIEW = "  Select o.id_order ,\n" +
            "        a.model,a.license_plate,\n" +
            "        u.id_user, u.login , u.telephone_number, u.email,\n" +
            "        CONCAT(\"(\", p1.district, \") \" , p1.street, \" \", p1.home_number, \" - (\",p1.district, \") \" ,p2.street, \" \", p2.home_number) as \"route\",\n" +
            "        o.ordering_date ,\n" +
            "        os.status\n" +
            " from `order` as o\n" +
            " inner join order_auto as oa   on  o.id_order=oa.id_order\n" +
            " inner join auto as a          on a.id_car=oa.id_car\n" +
            " inner join route as r1        on r1.id_order=o.id_order\n" +
            " inner join route as r2        on r2.id_order=o.id_order\n" +
            " inner join addresses as p1    on r1.id_address=p1.id_address and r1.priority=1\n" +
            " inner join addresses as p2    on r2.id_address=p2.id_address and r2.priority=2\n" +
            " inner join order_status as os on os.id_status=o.id_status\n" +
            " inner join `user` as u        on u.id_user=o.id_user\n" +
            " where o.id_user=?" +
            " ORDER BY o.id_order DESC;";

    private static final String GET_ALL_ORDER_VIEWS =  " Select o.id_order ,\n" +
            "        a.model,a.license_plate,\n" +
            "        u.id_user, u.login , u.telephone_number, u.email,\n" +
            "        CONCAT(\"(\", p1.district, \") \" , p1.street, \" \", p1.home_number, \" - (\",p1.district, \") \" ,p2.street, \" \", p2.home_number) as \"route\",\n" +
            "        o.ordering_date ,\n" +
            "        os.status\n" +
            " from `order` as o\n" +
            " inner join order_auto as oa   on  o.id_order=oa.id_order\n" +
            " inner join auto as a          on a.id_car=oa.id_car\n" +
            " inner join route as r1        on r1.id_order=o.id_order\n" +
            " inner join route as r2        on r2.id_order=o.id_order\n" +
            " inner join addresses as p1    on r1.id_address=p1.id_address and r1.priority=1\n" +
            " inner join addresses as p2    on r2.id_address=p2.id_address and r2.priority=2\n" +
            " inner join order_status as os on os.id_status=o.id_status\n" +
            " inner join `user` as u        on u.id_user=o.id_user" +
            " ORDER BY o.id_order DESC;";


    private static final String UPADTE_ORDER_STATUS = "update `order`\n" +
            "set id_status = ?\n" +
            "where\n" +
            "id_order = ?;";

    private static final String UPDATE_ORDER_STATUS_PROCEDURE = "call updateOrderStatus(?,?)";

    private static final String GET_FAVOURITE_USERS = "select u.email , sum(o.`sum`) as \"total\"\n" +
            "from user as u\n" +
            "inner join `order` as o\ton\tu.id_user=o.id_user\n" +
            "where o.id_status = 3\n" +
            "group by u.id_user\n" +
            "order by total DESC";

    public ArrayList<Zapros> takeFavor(){
        final  Connection connection = DBConfig.getInstance().takeConnection();
        ArrayList<Zapros> zaprosList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FAVOURITE_USERS);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 Zapros zapros = new Zapros();
                 zapros.setEmail(resultSet.getString("email"));
                 zapros.setSum(resultSet.getDouble("total"));
                 zaprosList.add(zapros);
             }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return zaprosList;
    }

    public void updateOrder(int orderId,int statusId){
        final Connection connection = DBConfig.getInstance().takeConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(UPDATE_ORDER_STATUS_PROCEDURE);
            callableStatement.setInt(1,orderId);
            callableStatement.setInt(2,statusId);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    public ArrayList<OrderView> takeOrderView(int userId){
        final Connection connection = DBConfig.getInstance().takeConnection();

        ArrayList<OrderView> orderViews = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_VIEW);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrderView orderView = new OrderView();
                orderView.setOrderId(resultSet.getInt("id_order"));
                orderView.setModel(resultSet.getString("model"));
                orderView.setLicensePlate(resultSet.getString("license_plate"));
                orderView.setUserId(resultSet.getInt("id_user"));
                orderView.setLogin(resultSet.getString("login"));
                orderView.setTelephoneNumber(resultSet.getString("telephone_number"));
                orderView.setEmail(resultSet.getString("email"));
                orderView.setRoute(resultSet.getString("route"));
                java.sql.Date sqlDate = resultSet.getDate("ordering_date");
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                orderView.setOrderDate(utilDate);
                orderView.setOrderStatus(resultSet.getString("status"));
                orderViews.add(orderView);
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return orderViews;
    }

    public ArrayList<OrderView> takeAllOrderViews(){
        ArrayList<OrderView> allOrderViews = new ArrayList<>();

        final Connection connection = DBConfig.getInstance().takeConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDER_VIEWS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                OrderView orderView = new OrderView();
                orderView.setOrderId(resultSet.getInt("id_order"));
                orderView.setModel(resultSet.getString("model"));
                orderView.setLicensePlate(resultSet.getString("license_plate"));
                orderView.setUserId(resultSet.getInt("id_user"));
                orderView.setLogin(resultSet.getString("login"));
                orderView.setTelephoneNumber(resultSet.getString("telephone_number"));
                orderView.setEmail(resultSet.getString("email"));
                orderView.setRoute(resultSet.getString("route"));
                java.sql.Date sqlDate = resultSet.getDate("ordering_date");
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                orderView.setOrderDate(utilDate);
                orderView.setOrderStatus(resultSet.getString("status"));
                allOrderViews.add(orderView);
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return allOrderViews;
    }


    public int create(Order entity, int userId){
        int orderId = 0;
        java.util.Date utilDate = entity.getOrderDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        final Connection connection = DBConfig.getInstance().takeConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1,entity.getSum());
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setInt(3, entity.getStatus().getStatusCode());
            preparedStatement.setInt(4,userId);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                orderId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return orderId;
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public void delete(Order entity) {

    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order entity) {
        return null;
    }
}
