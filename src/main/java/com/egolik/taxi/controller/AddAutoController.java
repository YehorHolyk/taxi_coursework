package com.egolik.taxi.controller;

import com.egolik.taxi.dao.*;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebResult;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addauto")
public class AddAutoController extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(DeleteAutoController.class);
    private AutoService autoService;
    private IMyDAO dao = DAOFactory.getDAOInstance(TypeDAO.MySQL);

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.autoService = (AutoService) config.getServletContext().getAttribute(AutoService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/addauto.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auto auto = new Auto();
        auto.setBrand(req.getParameter("brand"));
        auto.setModel(req.getParameter("model"));
        auto.setLicensePlate(req.getParameter("license_plate"));
        auto.setPrice((Double.parseDouble(req.getParameter("price"))));
        auto.setSeats(Integer.parseInt(req.getParameter("seats")));
        auto.setAutoClass(req.getParameter("class"));
        auto.setPictureURL(req.getParameter("pictureURL"));
        auto.setManufacturer(req.getParameter("manufacturer"));
        dao.create(auto);
        autoService.create(auto);
        resp.sendRedirect("/catalog");
    }
}
