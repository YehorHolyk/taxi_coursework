package com.egolik.taxi.controller;

import com.egolik.taxi.entity.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/shoppingcart")
public class ShoppingCardController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(ShoppingCardController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Shopping card!!");
//        Map<String, Auto> shoppingCart = (Map<String, Auto>) req.getSession().getAttribute("shoppingCart");
        req.getRequestDispatcher("/WEB-INF/jsp/shoppingcart.jsp").forward(req,resp);
    }
}
