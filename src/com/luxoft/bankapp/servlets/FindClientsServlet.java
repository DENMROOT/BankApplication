package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.BankDAO;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.services.BankService;
import com.luxoft.bankapp.service.services.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 20.02.2015.
 */
public class FindClientsServlet extends HttpServlet {
    Logger logger = Logger.getLogger("FindClientsServlet");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String clientName = (String) request.getParameter("clientName");
        String city = (String) request.getParameter("city");
        logger.info("FindClients command for ClientName: " + clientName + " city:" + city);

        BankDAO bankDao = DaoFactory.getBankDAO();
        Bank bank = bankDao.getBankByName("My Bank");
        BankService bankService = ServiceFactory.getBankServiceImpl();

        List<Client> clientsByCityName = bankService.findClientsByCityName(bank, city, clientName);

        for (Client iterator: clientsByCityName) {
            logger.info("Found ClientName: " + iterator.getName() + " " + iterator.getCity());
        }


        request.setAttribute("city", city);
        request.setAttribute("clientName", clientName);
        request.setAttribute("clients", clientsByCityName);
        request.getRequestDispatcher("/findclient.jsp").forward(request, response);

    }
}
