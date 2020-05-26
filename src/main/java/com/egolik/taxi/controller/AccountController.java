package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.dao.MySQLDAO;
import com.egolik.taxi.entity.OrderView;
import com.egolik.taxi.service.OrderService;
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


@WebServlet("/account")
public class AccountController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = (OrderService) config.getServletContext().getAttribute(OrderService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Account!!");
        req.setAttribute("title", "Account");
        MySQLDAO mySQLDAO = new MySQLDAO();
        ArrayList<OrderView> orderViews = mySQLDAO.takeOrderView((Integer) req.getSession().getAttribute("userId"))/*orderService.takeOrderView((Integer) req.getSession().getAttribute("userId"))*/;
        //ArrayList<OrderView> combinedViews = combine(orderViews);
        req.getSession().setAttribute("orderViews", orderViews);
        req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
    }
/*
    public ArrayList<OrderView> combine(ArrayList<OrderView> orderViews) {
        ArrayList<OrderView> newOrderViews = new ArrayList<>();
        for (OrderView orderView : orderViews) {
            if (orderViews.indexOf(orderView) + 1 == orderViews.size()) {
                break;
            } else if (orderViews.get(orderViews.indexOf(orderView)).getOrderId() ==
                    orderViews.get(orderViews.indexOf(orderView) + 1).getOrderId()) {
                OrderView ov = orderView;
                String model = orderViews.get(orderViews.indexOf(orderView) + 1).getModel();
                ov.setModel(ov.getModel() + ", " + model);
                String licensePlate = orderViews.get(orderViews.indexOf(orderView) + 1).getLicensePlate();
                ov.setLicensePlate(ov.getLicensePlate() + ", " + licensePlate);
                newOrderViews.add(ov);
            }

        }
        return newOrderViews;
    */
}
