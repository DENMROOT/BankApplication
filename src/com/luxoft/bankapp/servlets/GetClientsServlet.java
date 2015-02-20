package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.BankDAO;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.services.BankService;
import com.luxoft.bankapp.service.services.ClientService;
import com.luxoft.bankapp.service.services.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 20.02.2015.
 */
public class GetClientsServlet extends HttpServlet {
    Logger logger = Logger.getLogger("GetClientsServlet");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String clientName = (String) request.getParameter("clientName");
        logger.info("Client Detailed info for Client: " + clientName);

        BankDAO bankDao = DaoFactory.getBankDAO();
        Bank bank = bankDao.getBankByName("My Bank");
        ClientService clientService = ServiceFactory.getClientServiceImpl();

        Client client = clientService.findClientByName(bank, clientName);
        logger.info("Client Detailed info for Client: " + client);

        request.setAttribute("client", client);

        /*
        request.setAttribute("name", client.getName());
        logger.info("Client name: " + request.getAttribute("name"));
        request.setAttribute("city", client.getCity());
        logger.info("Client city: " + request.getAttribute("city"));
        request.setAttribute("gender", client.getGender());
        logger.info("Client gender: " + request.getAttribute("gender"));
        request.setAttribute("email", client.getEmail());
        logger.info("Client email: " + request.getAttribute("email"));
        request.setAttribute("balance", client.getBalance());
        logger.info("Client balance: " + request.getAttribute("balance"));
*/
        request.getRequestDispatcher("/client.jsp").forward(request, response);

    }
}
