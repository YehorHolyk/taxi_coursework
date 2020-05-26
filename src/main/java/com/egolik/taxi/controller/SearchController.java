package com.egolik.taxi.controller;

import com.egolik.taxi.dao.DAOFactory;
import com.egolik.taxi.dao.IMyDAO;
import com.egolik.taxi.dao.TypeDAO;
import com.egolik.taxi.entity.Auto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {

    private IMyDAO dao = DAOFactory.getDAOInstance(TypeDAO.MySQL);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String classAuto = req.getParameter("classAuto");
        final double minPrice = Double.parseDouble(req.getParameter("minPrice"));
        final double maxPrice = Double.parseDouble(req.getParameter("maxPrice"));
        List<Auto> autoList = dao.findAutoByClassAndPrice(classAuto,minPrice,maxPrice);
        req.setAttribute("autos",autoList);
        req.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(req,resp);

    }
}
