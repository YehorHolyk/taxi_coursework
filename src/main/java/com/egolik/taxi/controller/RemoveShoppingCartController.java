package com.egolik.taxi.controller;

import com.egolik.taxi.entity.Auto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/removeFromShoppingCart")
public class RemoveShoppingCartController  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String licensePlate = req.getParameter("license_plate");

        Map<String,Auto> shoppingCart = (Map<String, Auto>) req.getSession().getAttribute("shoppingCart");

        req.getSession().removeAttribute("shoppingCart");

        shoppingCart.remove(licensePlate);

        req.getSession().setAttribute("shoppingCart",shoppingCart);

        resp.sendRedirect("/shoppingcart");
    }
}
