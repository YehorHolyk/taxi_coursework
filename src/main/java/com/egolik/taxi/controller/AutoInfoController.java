package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AutoInfo")
public class AutoInfoController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(AutoInfoController.class);
    private AutoService autoService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.autoService = (AutoService) config.getServletContext().getAttribute(AutoService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Details!!");
        String licensePlate = req.getParameter("license_plate");
        Auto auto = autoService.takeAuto(licensePlate);
        req.setAttribute("auto",auto);
        req.getRequestDispatcher("/WEB-INF/jsp/autoinfo.jsp").forward(req,resp);

    }
}
