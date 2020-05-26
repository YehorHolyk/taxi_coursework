package com.egolik.taxi.dao;

import com.egolik.taxi.entity.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mysql.cj.util.StringUtils;
import org.bson.Document;

import java.util.*;


public class MongoDBDAO implements IMyDAO {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection user;
    private MongoCollection order;
    private MongoCollection route;
    private MongoCollection order_auto;
    private MongoCollection auto;
    private MongoCollection manufacturer;
    private MongoCollection addresses;
    private int countAuto = 0;
    private int countOrder = 0;
    private int countRoute = 0;
    private int countAddress = 0;
    private int countUsers = 0;

    public MongoDBDAO() {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.db = mongoClient.getDatabase("taxi");
        this.user = db.getCollection("user");
        this.order = db.getCollection("order");
        this.route = db.getCollection("route");
        this.order_auto = db.getCollection("order_auto");
        this.auto = db.getCollection("auto");
        this.manufacturer = db.getCollection("manufacturer");
        this.addresses = db.getCollection("addresses");
    }

    @Override
    public Optional<User> findByLoginOrEmail(String login, String email) {
        if (StringUtils.isNullOrEmpty(login) || StringUtils.isNullOrEmpty(email)) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean isUserExists(String email) {
        if (StringUtils.isNullOrEmpty(email)) {
            return false;
        }
        Document byEmail = new Document();
        byEmail.put("email", email);
        FindIterable<Document> client = null;
        client = user.find(byEmail);
        if (client != null) {
            return true;
        }
        return false;
    }

    @Override
    public User takeUser(String email) {
        User user1 = new User();
        Document byEmail = new Document();
        byEmail.put("email", email);
        FindIterable<Document> client = null;
        client = user.find(byEmail);
        int statusCode = 1;
        if (client.first().getString("status").equals("ADMIN")) {
            statusCode = 2;
        }
        user1.setId(client.first().getInteger("id"));
        user1.setTelephoneNumber(client.first().getString("telephone_number"));
        user1.setPassword(client.first().getString("password"));
        user1.setEmail(client.first().getString("email"));
        user1.setStatus(UserRole.getRoleByStatusCode(statusCode));
        user1.setName(client.first().getString("name"));
        user1.setLogin(client.first().getString("login"));
        return user1;
    }

    @Override
    public void create(User entity) {
        getCounts();
        Document client = new Document();
        countUsers++;
        client.put("id", countUsers);
        client.put("login", entity.getLogin());
        client.put("name", entity.getName());
        client.put("email", entity.getEmail());
        client.put("status", "USER");
        client.put("password", entity.getPassword());
        client.put("telephone_number", entity.getTelephoneNumber());
        user.insertOne(client);
    }

    @Override
    public void updateOrder(int orderId, int statusId) {
        String s = "";
        switch (statusId){
            case 1: s = "ON HOLD"; break;
            case 2: s = "IN PROCESSING";break;
            case 3: s = "COMPLETED";break;
            case 4: s = "CANCELED"; break;
        }
        Document doc = new Document();
        doc.put("status", s);
        BasicDBObject search = new BasicDBObject().append("id",orderId);
        order.updateOne(search,doc);
    }

    @Override
    public ArrayList<OrderView> takeOrderView(int userId) {
        OrderView orderView = new OrderView();
        ArrayList<OrderView> orderViews = new ArrayList<>();
        FindIterable<Document> resultOrder = null;
        resultOrder = order.find(new Document("id_user",userId));
        for(Document doc: resultOrder){
            orderView.setOrderId(doc.getInteger("id"));
            orderView.setOrderDate(doc.getDate("ordering_date"));
            orderView.setOrderStatus("status");
            orderView.setLicensePlate("");
            orderView.setModel("");
            orderView.setRoute("");
            orderView.setEmail("");
            orderView.setTelephoneNumber("");
            orderView.setUserId(userId);
            orderViews.add(orderView);
        }
        return orderViews;
    }

    @Override
    public ArrayList<OrderView> takeAllOrderViews() {
        OrderView orderView = new OrderView();
        ArrayList<OrderView> orderViews = new ArrayList<>();
        FindIterable<Document> resultOrder = null;
        resultOrder = order.find();
        for(Document doc: resultOrder){
            orderView.setOrderId(doc.getInteger("id"));
            orderView.setOrderDate(doc.getDate("ordering_date"));
            orderView.setOrderStatus("status");
            orderView.setLicensePlate("");
            orderView.setModel("");
            orderView.setRoute("");
            orderView.setEmail("");
            orderView.setTelephoneNumber("");
            orderView.setUserId(doc.getInteger("id_user"));
            orderViews.add(orderView);
        }
        return orderViews;
    }

    @Override
    public int create(Order entity, int userId) {
        getCounts();
        Document order = new Document();
        countOrder++;
        order.put("id", countOrder);
        order.put("sum", entity.getSum());
        order.put("ordering_date", entity.getOrderDate());
        order.put("status", "ON HOLD");
        order.put("id_user", userId);
        this.order.insertOne(order);
        return 0;
    }

    @Override
    public void create(int orderId, List<Integer> addressesId) {
        getCounts();
        for (int id : addressesId) {
            Document rou = new Document();
            countRoute++;
            rou.put("id", countRoute);
            rou.put("priority",addressesId.indexOf(id) + 1);
            rou.put("address_id", id);
            rou.put("order_id", orderId);
            route.insertOne(rou);
        }
    }

    @Override
    public void create(int orderId, HashMap<String, Auto> autos) {

    }

    @Override
    public List<Auto> getAllAuto() {
        List<Auto> autoList = new ArrayList<>();
        Auto car = new Auto();
        FindIterable<Document> result = null;
        result = auto.find();
        for (Document doc : result) {
//            int statusCode = 1;
//            if (doc.get("status").equals("ADMIN")) {
//                statusCode = 2;
//            }
            car.setId(doc.getInteger("id"));
            car.setManufacturer(doc.getString("manufacturer"));
            car.setPictureURL(doc.getString("pictureURL"));
            car.setAutoClass(doc.getString("class"));
            car.setSeats(doc.getInteger("seats"));
            car.setPrice(doc.getDouble("price"));
            car.setLicensePlate(doc.getString("license_plate"));
            car.setModel(doc.getString("model"));
            car.setBrand(doc.getString("brand"));
            autoList.add(car);
        }
        return autoList;
    }

    @Override
    public Auto getByLicensePlate(String licensePlate) {
        Auto car = new Auto();
        Document byLicense = new Document();
        byLicense.put("license_plate", licensePlate);
        FindIterable<Document> result = null;
        result = auto.find(byLicense);
//        int statusCode = 1;
//        if (result.first().getString("status").equals("ADMIN")) {
//            statusCode = 2;
//        }
        car.setId(result.first().getInteger("id"));
        car.setManufacturer(result.first().getString("manufacturer"));
        car.setPictureURL(result.first().getString("pictureURL"));
        car.setAutoClass(result.first().getString("class"));
        car.setSeats(result.first().getInteger("seats"));
        car.setPrice(result.first().getDouble("price"));
        car.setLicensePlate(result.first().getString("license_plate"));
        car.setModel(result.first().getString("model"));
        car.setBrand(result.first().getString("brand"));
        return car;
    }

    @Override
    public void deleteAutoByLicensePlate(String licensePlate) {
        auto.deleteOne(new Document("license_plate", licensePlate));
    }

    @Override
    public void create(Auto entity) {
        getCounts();
        Document car = new Document();
        countAuto++;
        car.put("id", countAuto);
        car.put("brand", entity.getBrand());
        car.put("manufacturer", entity.getManufacturer());
        car.put("license_pale", entity.getLicensePlate());
        car.put("price", entity.getPrice());
        car.put("class", entity.getAutoClass());
        car.put("seats", entity.getSeats());
        car.put("pictureURL", entity.getPictureURL());
        auto.insertOne(car);
    }

    @Override
    public List<Integer> create(TreeMap<Integer, Address> addressMap) {
        getCounts();
        List<Integer> addressesId = new ArrayList<>();
        for (Map.Entry<Integer, Address> entry : addressMap.entrySet()) {
            Document addr = new Document();
            countAddress++;
            addr.put("id", countAddress);
            addr.put("district", entry.getValue().getDistrict());
            addr.put("street", entry.getValue().getStreet());
            addr.put("home_number", entry.getValue().getHomeNumber());
            int m = countAddress;
            addressesId.add(m);
            addresses.insertOne(addr);
        }
        return addressesId;
    }

    @Override
    public List<Auto> findAutoByClassAndPrice(String classAuto, double minPrice, double maxPrice) {
        return null;
    }

    void getCounts() {
        countAuto = (int) auto.count();
        countOrder = (int) order.count();
        countRoute = (int) route.count();
        countAddress = (int) addresses.count();

    }
}
