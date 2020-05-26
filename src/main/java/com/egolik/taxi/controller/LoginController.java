package com.egolik.taxi.controller;

import com.egolik.taxi.constants.RequestConstants;
import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.Address;
import com.egolik.taxi.entity.Auto;
import com.egolik.taxi.entity.User;
import com.egolik.taxi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute(UserService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Login!!");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.removeAttribute("error_map");

        Map<String,Auto> shoppingCart = new HashMap<>();


        Map<String, String> errorMap = new HashMap<>();
        final String email = req.getParameter(RequestConstants.USER_EMAIL_FIELD);

        if(!userService.isExistByEmail(email)){
            errorMap.put("error_email", "User doesn't exist!");
            req.setAttribute("error_map", errorMap);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        User user = userService.takeUser(email);
        final String password = DigestUtils.md5Hex(req.getParameter(RequestConstants.USER_PASSWORD_FIELD));
        if(!user.getPassword().equals(password)){
            errorMap.put("error_password", "Wrong password");
        }

        if (!errorMap.isEmpty()) {
            req.setAttribute("error_map", errorMap);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        Map<Integer, Address> addressMap = new TreeMap<>();
        Map <String,String> userMap = new HashMap<>();
        int userId = user.getId();
        userMap.put("userLogin" , user.getLogin());
        userMap.put("userName" , user.getName());
        userMap.put("userStatus" , String.valueOf(user.getStatus()));
        userMap.put("userEmail" , user.getEmail());
        userMap.put("userTelephoneNumber" , user.getTelephoneNumber());

        req.getSession().setAttribute("userId",userId);
        req.getSession().setAttribute("user", userMap);
        req.getSession().setAttribute("shoppingCart",shoppingCart);
        req.getSession().setAttribute("addresses", addressMap);

        resp.sendRedirect("/account");
    }
}
