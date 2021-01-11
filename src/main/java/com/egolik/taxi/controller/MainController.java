package com.egolik.taxi.controller;

import com.egolik.taxi.dao.TestDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class MainController extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Main");
        req.setAttribute("title", "ky Egor");
        req.setAttribute("test", "ky Egor");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
