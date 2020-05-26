package com.egolik.taxi.controller;

import com.egolik.taxi.constants.RequestConstants;
import com.egolik.taxi.dao.MongoDBDAO;
import com.egolik.taxi.entity.User;
import com.egolik.taxi.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(SignUpController.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute(UserService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Register!!");
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.removeAttribute("error_map");

        Map<String, String> errorMap = new HashMap<>();
        final String login = request.getParameter(RequestConstants.USER_LOGIN_FIELD);
        final String email = request.getParameter(RequestConstants.USER_EMAIL_FIELD);

        if (userService.isExistByLoginOrEmail(login, email)) {
            errorMap.put("error_login_email", "User exists!");
        }

        final Optional<String> encryptPass = validateAndEncryptPassword(request);
        if (!encryptPass.isPresent()) {
            errorMap.put("error_pass", "Password not equals");
        }

        if (!errorMap.isEmpty()) {
            request.setAttribute("error_map", errorMap);
            request.getRequestDispatcher("signup.jsp").forward(request, resp);
            return;
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(encryptPass.get());
        user.setName(request.getParameter(RequestConstants.USER_NAME_FIELD));
        user.setEmail(email);
        user.setTelephoneNumber(request.getParameter(RequestConstants.USER_TEL_FIELD));

        userService.create(user);


        resp.sendRedirect("login.jsp");
    }

    private Optional<String> validateAndEncryptPassword(HttpServletRequest request) {
        final String pass = request.getParameter(RequestConstants.USER_PASSWORD_FIELD);
        final String passConfirm = request.getParameter(RequestConstants.USER_PASSWORD_CONFIRM_FIELD);

        if (StringUtils.isNullOrEmpty(pass) || StringUtils.isNullOrEmpty(passConfirm) || !pass.equals(passConfirm)) {
            return Optional.empty();
        }

        return Optional.of(DigestUtils.md5Hex(pass));
    }

}
