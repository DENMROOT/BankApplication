package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.DAO.BankDAO;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.services.ClientService;
import com.luxoft.bankapp.service.services.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 20.02.2015.
 */
public class SaveClientServlet extends HttpServlet {
    Logger logger = Logger.getLogger("SaveClientServlet");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Long clientId = Long.parseLong((String) request.getParameter("id"));
        String name = (String) request.getParameter("name");
        String city = (String) request.getParameter("city");
        String gender = (String) request.getParameter("gender");
        String email = (String) request.getParameter("email");

        logger.info("Client Detailed info for Client: " + clientId + name + city + gender +  email);

        BankDAO bankDao = DaoFactory.getBankDAO();
        ClientService clientService = ServiceFactory.getClientServiceImpl();
        Bank bank = bankDao.getBankByName("My Bank");
        Client client = clientService.findClientById(bank, clientId);

        client.setName(name);
        client.setCity(city);
        switch (gender) {
            case "MALE" : client.setGender(Gender.MALE); break;
            case "FEMALE" : client.setGender(Gender.FEMALE); break;
        }

        client.setEmail(email);

        clientService.saveClient(client);
        client = clientService.findClientById(bank, clientId);
        request.setAttribute("client", client);

        request.getRequestDispatcher("/client.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
