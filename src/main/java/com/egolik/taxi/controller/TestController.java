package com.egolik.taxi.controller;

import com.egolik.taxi.service.TestService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/test")
public class TestController extends HttpServlet {

    private TestService testService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> testData = testService.getAllTables();

        req.setAttribute("tables", testData);
        req.getRequestDispatcher("/test.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        testService = (TestService) config.getServletContext().getAttribute(TestService.class.getName());
    }
}
