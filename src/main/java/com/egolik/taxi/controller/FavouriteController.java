package com.egolik.taxi.controller;

import com.egolik.taxi.entity.Zapros;
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
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/zapros")
public class FavouriteController extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = (OrderService) config.getServletContext().getAttribute(OrderService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Zapros> list = orderService.takeFavor();
        req.setAttribute("rs",list);
        req.getRequestDispatcher("zapros.jsp").forward(req,resp);
    }
}
