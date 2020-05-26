package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.service.AutoService;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/addToShoppingCart")
public class AddShoppingCartController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(AddShoppingCartController.class);
    private AutoService autoService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.autoService = (AutoService) config.getServletContext().getAttribute(AutoService.class.getName());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.removeAttribute("error_map");

        String licensePlate = req.getParameter("license_plate");

        Map<String,Auto> shoppingCart = (Map<String, Auto>) req.getSession().getAttribute("shoppingCart");

        Map<String, String> errorMap = new HashMap<>();

        Auto auto = autoService.takeAuto(licensePlate);

        shoppingCart.put(licensePlate,auto);

        req.getSession().setAttribute("shoppingCart",shoppingCart);

        resp.sendRedirect("/catalog");
    }
}
