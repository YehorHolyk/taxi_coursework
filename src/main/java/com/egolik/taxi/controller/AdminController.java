package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.OrderView;
import com.egolik.taxi.service.AddressService;
import com.egolik.taxi.service.OrderAutoService;
import com.egolik.taxi.service.OrderService;
import com.egolik.taxi.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = (OrderService) config.getServletContext().getAttribute(OrderService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Admin");
        ArrayList<OrderView> allOrders = orderService.takeAllOrderViews();
        req.setAttribute("orders",allOrders);
        req.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
