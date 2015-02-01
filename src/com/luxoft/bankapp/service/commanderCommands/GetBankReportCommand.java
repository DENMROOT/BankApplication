package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankInfo;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.BankReport;
import com.luxoft.bankapp.model.BankReportContainer;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.clientServer.BankServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Денис on 25.01.2015.
 */
public class GetBankReportCommand implements Command {
    @Override
    public void execute() {
        BankInfo myBankInfo = new BankInfo();
        BankDAOImpl myBankDao = new BankDAOImpl();
        myBankInfo = myBankDao.getBankInfo(BankCommander.currentBank);

        System.out.println("Количество клиентов: " + myBankInfo.getNumberOfClients());
        System.out.println("Суммарный баланс по всем счетам: " + myBankInfo.getTotalAccountSum());
        System.out.println("Клиенты по городам: " + myBankInfo.getClientsByCity());

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {

        System.out.println(bank.getBankID());

        try {
            BankInfo myBankInfo = new BankInfo();
            BankDAOImpl myBankDao = new BankDAOImpl();
            myBankInfo = myBankDao.getBankInfo(bank);

            ObjectOutputStream outObj = new ObjectOutputStream(out);
            outObj.writeObject(myBankInfo);
        } catch (IOException e) {
            e.getMessage();
        }

    }

    @Override
    public void printCommandInfo() { System.out.println("Get BankInfo report command"); }
}
