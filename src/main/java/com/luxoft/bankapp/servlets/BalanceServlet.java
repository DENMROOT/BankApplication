package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.AccountDAO;
import com.luxoft.bankapp.service.DAO.BankDAO;
import com.luxoft.bankapp.service.DAO.ClientDAO;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.services.ClientService;
import com.luxoft.bankapp.service.services.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 13.02.2015.
 */
public class BalanceServlet extends HttpServlet {
    Logger logger = Logger.getLogger("BalanceServlet");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String clientName = (String) session.getAttribute("clientName");

        logger.info("Balance command received for " + clientName);
        BankDAO bankDao = DaoFactory.getBankDAO();
        ClientService clientService = ServiceFactory.getClientServiceImpl();
        Bank bank = bankDao.getBankByName("My Bank");
        Client client = clientService.findClientByName(bank, clientName);
        float balance = clientService.getClientBalance(bank, client);
        logger.info("Balance command for " + clientName + " : " + balance);


        /*
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("text/html; charset=UTF-8");
        out.write(clientName.getBytes("UTF-8"));
        out.println("<h2>Current client balance: " + balance + "</h2>");
        */

        request.setAttribute("balance", balance);
        request.getRequestDispatcher("/balance.jsp").forward(request, response);

    }
}
