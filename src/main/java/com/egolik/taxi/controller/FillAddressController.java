package com.egolik.taxi.controller;

import com.egolik.taxi.entity.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/filladdress")
public class FillAddressController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(ShoppingCardController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().getAttribute("addresses");
        req.getRequestDispatcher("/WEB-INF/jsp/filladdress.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Integer, Address> addressMap = (Map<Integer, Address>) req.getSession().getAttribute("addresses");
        req.getSession().removeAttribute("addresses");

        Address address = new Address()
                .setDistrict(req.getParameter("district"))
                .setHomeNumber(req.getParameter("homeNumber"))
                .setStreet(req.getParameter("street"));

        addressMap.put(addressMap.size()+1,address);

        req.getSession().setAttribute("addresses",addressMap);
        req.getRequestDispatcher("/WEB-INF/jsp/filladdress.jsp").forward(req,resp);
    }
}
