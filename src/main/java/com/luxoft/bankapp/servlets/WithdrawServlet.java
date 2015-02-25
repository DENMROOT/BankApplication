package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.BankDAO;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.services.AccountService;
import com.luxoft.bankapp.service.services.BankService;
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
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 13.02.2015.
 */
public class WithdrawServlet extends HttpServlet {
    public final static Logger logger = Logger.getLogger(WithdrawServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String clientName = (String) session.getAttribute("clientName");
        Float amount = Float.parseFloat(request.getParameter("amount"));

        logger.info("Withdraw command received for " + clientName);
        BankDAO bankDao = DaoFactory.getBankDAO();
        final ServletContext context = getServletContext();
        BankService bankService = (BankService) context.getAttribute("bankService");
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        Bank bank = bankDao.getBankByName("My Bank");
        Client client = clientService.findClientByName(bank, clientName);
        try {
            accountService.withdrawFromAccount(client,client.getActiveAccount(),amount);
        } catch (NotEnoughFundsException e) {
            throw new ServletException("Недостаточно средств на счете");
        }

        response.sendRedirect("/balance");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
