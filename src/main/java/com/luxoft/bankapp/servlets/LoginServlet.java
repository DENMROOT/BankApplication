package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 12.02.2015.
 */
public class LoginServlet extends HttpServlet {
    Logger logger = Logger.getLogger("LoginServlet");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final String clientName = request.getParameter("clientName");
        if (clientName == null) {
            logger.warning("Client not found!!!");
            throw new ServletException("No client specified");
        }
        request.getSession().setAttribute("clientName", clientName);
        logger.info("Client: " + clientName + " logged into ATM");

        response.sendRedirect("/menu.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
