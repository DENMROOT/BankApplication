package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 13.02.2015.
 */
public class LogoutServlet extends HttpServlet {
    public final static  Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String clientName = (String) session.getAttribute("clientName");
        session.invalidate();
        logger.info("User: " +  clientName + " - logged out");
        response.sendRedirect("/");
    }
}
