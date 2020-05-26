package com.egolik.taxi.controller;

import com.egolik.taxi.dao.MongoDBDAO;
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

@WebServlet("/DeleteAuto")
public class DeleteAutoController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(DeleteAutoController.class);
    private AutoService autoService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.autoService = (AutoService) config.getServletContext().getAttribute(AutoService.class.getName());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Deleting auto...");
        String licensePlate = req.getParameter("license_plate");


        autoService.deleteAuto(licensePlate);

        req.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(req,resp);
    }
}
