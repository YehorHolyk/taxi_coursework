package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.Address;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.entity.Order;
import com.egolik.taxi.entity.OrderStatus;
import com.egolik.taxi.service.AddressService;
import com.egolik.taxi.service.OrderAutoService;
import com.egolik.taxi.service.OrderService;
import com.egolik.taxi.service.RouteService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/order")
public class OrderController extends HttpServlet {

    private AddressService addressService;
    private RouteService routeService;
    private OrderService orderService;
    private OrderAutoService orderAutoService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.addressService = (AddressService) config.getServletContext().getAttribute(AddressService.class.getName());
        this.routeService = (RouteService) config.getServletContext().getAttribute(RouteService.class.getName());
        this.orderService = (OrderService) config.getServletContext().getAttribute(OrderService.class.getName());
        this.orderAutoService = (OrderAutoService) config.getServletContext().getAttribute(OrderAutoService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (int) req.getSession().getAttribute("userId");
        Map<Integer, Address> addressMap = new TreeMap<>();
        addressMap = (Map<Integer, Address>) req.getSession().getAttribute("addresses");
        Map<String, Auto> shoppingCart = new HashMap<>();
        shoppingCart = (Map<String, Auto>) req.getSession().getAttribute("shoppingCart");
        Order order = new Order();



        order.setOrderDate((Date) req.getSession().getAttribute("date"));
        order.setStatus(OrderStatus.ON_HOLD);
        order.setUserId(userId);
        order.setSum((Double) req.getSession().getAttribute("summ"));
        int orderId = orderService.createOrder(order,userId);
        List<Integer> addressesId = addressService.createAddresses((TreeMap<Integer, Address>) addressMap);
        routeService.createRoute(orderId,addressesId);
        orderAutoService.createOrderAuto(orderId, (HashMap<String, Auto>) shoppingCart);

        shoppingCart = new HashMap<>();
        addressMap = new TreeMap<>();
        req.getSession().setAttribute("addresses",addressMap);
        req.getSession().setAttribute("shoppingCart",shoppingCart);
        resp.sendRedirect("/account");
    }
}
