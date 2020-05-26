package com.egolik.taxi.dao;

import com.egolik.taxi.configuration.DBConfig;
import com.egolik.taxi.constants.DBConstants;
import com.egolik.taxi.entity.*;
import com.egolik.taxi.exception.AppException;
import com.mysql.cj.util.StringUtils;

import java.sql.*;
import java.util.*;

public class MySQLDAO implements IMyDAO {

    private static final String INSERT_NEW_USER = "INSERT INTO user (login,name,id_status,email,password,telephone_number) " +
            "VALUES (?,?,?,?,?,?);";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user;";
    private static final String SELECT_USER_BY_LOGIN_OR_EMAIL = "Select * from user where login = ? or email = ?";
    private static final String INSERT_NEW_ADDRESS = "INSERT INTO addresses (district,street,home_number) " +
            "VALUES (?,?,?);";
    private static final String SELECT_ALL_AUTO = "SELECT * FROM auto order by id_car desc;";
    private static final String SELECT_BY_LICENSE_PLATE = "SELECT * FROM auto WHERE license_plate=?;";
    private static final String DELETE_BY_LICENSE_PLATE = "DELETE FROM auto WHERE license_plate=?";
    private static final String INSERT_AUTO = "INSERT INTO auto(brand,model,license_plate,price,class," +
            "seats,pictureURL,manufacturer_id) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SELECT_MANUFACTURER = "SELECT * FROM manufacturer WHERE manufacturer=?";
    private static final String INSERT_MANUFACTURER = "INSERT INTO manufacturer(manufacturer) VALUES (?)";
    private final String INSERT_NEW_ROUTE = "INSERT INTO route (priority,id_address,id_order) " +
            "VALUES (?,?,?);";
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
    private final String INSERT_ORDER_AUTO = "INSERT INTO order_auto (id_order,id_car) " +
            "VALUES (?,?);";
    private final String SELECT_AUTO_BY_CLASS_AND_PRICE = "SELECT * FROM auto as a WHERE a.class=? AND a.price > ? AND a.price < ?;";


    @Override
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
            User user = new User();
            user.setId(resultSet.getInt(DBConstants.USER_ID));
            user.setName(resultSet.getString(DBConstants.USER_NAME));
            user.setStatus(UserRole.getRoleByStatusCode(resultSet.getInt(DBConstants.USER_ID_STATUS)));
            user.setLogin(resultSet.getString(DBConstants.USER_LOGIN));
            user.setEmail(resultSet.getString(DBConstants.USER_EMAIL));
            user.setTelephoneNumber(resultSet.getString(DBConstants.USER_TELEPHONE_NUMBER));
            if (resultSet.next()) {
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    @Override
    public boolean isUserExists(String email) {
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

    @Override
    public User takeUser(String email) {
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

//    @Override
//    public User mapToUser(ResultSet resultSet) {
//        try {
//            User user = new User();
//            user.setId(resultSet.getInt(DBConstants.USER_ID));
//            user.setName(resultSet.getString(DBConstants.USER_NAME));
//            user.setStatus(UserRole.getRoleByStatusCode(resultSet.getInt(DBConstants.USER_ID_STATUS)));
//            user.setLogin(resultSet.getString(DBConstants.USER_LOGIN));
//            user.setEmail(resultSet.getString(DBConstants.USER_EMAIL));
//            user.setTelephoneNumber(resultSet.getString(DBConstants.USER_TELEPHONE_NUMBER));
//            return user;
//        } catch (SQLException e) {
//            throw new AppException(e);
//        }
//    }

    @Override
    public void updateOrder(int orderId, int statusId) {
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

    @Override
    public ArrayList<OrderView> takeOrderView(int userId) {
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

    @Override
    public ArrayList<OrderView> takeAllOrderViews() {
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

    @Override
    public int create(Order entity, int userId) {
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
    public void create(int orderId, List<Integer> addressesId) {
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
    public void create(int orderId, HashMap<String, Auto> autos) {
        final Connection connection = DBConfig.getInstance().takeConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_AUTO);
            for(Map.Entry<String,Auto> entry : autos.entrySet()){
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2,entry.getValue().getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    @Override
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

    @Override
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

    @Override
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
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new AppException(e);
        }
    }

    @Override
    public List<Integer> create(TreeMap<Integer, Address> addressMap) {
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
    public List<Auto> findAutoByClassAndPrice(String classAuto, double minPrice, double maxPrice) {
        final Connection connection = DBConfig.getInstance().takeConnection();
        List<Auto> autoList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTO_BY_CLASS_AND_PRICE);
            preparedStatement.setString(1, classAuto);
            preparedStatement.setDouble(2, minPrice);
            preparedStatement.setDouble(3, maxPrice);
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
}
