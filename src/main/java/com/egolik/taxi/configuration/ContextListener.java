package com.egolik.taxi.configuration;

import com.egolik.taxi.dao.*;
import com.egolik.taxi.entity.Address;
import com.egolik.taxi.entity.OrderAuto;
import com.egolik.taxi.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserDao userDao = new UserDao();
        AutoDao autoDao = new AutoDao();
        OrderDao orderDao = new OrderDao();
        AddressDao addressDao = new AddressDao();
        OrderAutoDao orderAutoDao = new OrderAutoDao();
        RouteDao routeDao = new RouteDao();

        UserService userService = new UserService(userDao);
        AutoService autoService = new AutoService(autoDao);
        OrderService orderService = new OrderService(orderDao);
        AddressService addressService = new AddressService(addressDao);
        OrderAutoService orderAutoService = new OrderAutoService(orderAutoDao);
        RouteService routeService = new RouteService(routeDao);


        context.setAttribute(AutoService.class.getName(), autoService);
        context.setAttribute(UserService.class.getName(), userService);
        context.setAttribute(OrderService.class.getName(),orderService);
        context.setAttribute(AddressService.class.getName(),addressService);
        context.setAttribute(OrderAutoService.class.getName(),orderAutoService);
        context.setAttribute(RouteService.class.getName(),routeService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
