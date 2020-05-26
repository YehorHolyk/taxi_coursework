package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.service.AutoService;
import com.egolik.taxi.service.UserService;
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
import java.util.List;

@WebServlet("/catalog")
public class CatalogController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(CatalogController.class);
    private AutoService autoService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        this.autoService = (AutoService) config.getServletContext().getAttribute(AutoService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Catalog!!");
        List<Auto> autoList = autoService.takeAutos();
        req.setAttribute("autos",autoList);
        req.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(req,resp);
    }
}
