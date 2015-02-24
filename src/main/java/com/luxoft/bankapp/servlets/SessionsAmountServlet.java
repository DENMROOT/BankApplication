package com.luxoft.bankapp.servlets;

import org.apache.log4j.BasicConfigurator;
import org.h2.engine.Session;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 13.02.2015.
 */
public class SessionsAmountServlet extends HttpServlet {
    Logger logger = Logger.getLogger("SessionsAmountServlet");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletContext context = getServletContext();
        Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
        logger.info("ClientsConnected: " + clientsConnected);

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("text/html");
        out.println("Currently we have: " + clientsConnected + " connected Clients");
    }
}
