package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankReport;
import com.luxoft.bankapp.service.BankReportContainer;
import com.luxoft.bankapp.service.Command;
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

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {

        try {
            BankReport bankReport = new BankReport();
            BankReportContainer bankReportContainer = new BankReportContainer();
            bankReportContainer.setNumberOfClients(bankReport.getNumberOfClients(BankServer.currentBank));
            bankReportContainer.setAccountsNumber(bankReport.getAccountsNumber(BankServer.currentBank));
            bankReportContainer.setClientsSorted(bankReport.getClientsSorted(BankServer.currentBank));
            bankReportContainer.setBankCreditSum(bankReport.getBankCreditSum(BankServer.currentBank));
            bankReportContainer.setClientsByCity(bankReport.getClientsByCity(BankServer.currentBank));

            ObjectOutputStream outObj = new ObjectOutputStream(out);
            outObj.writeObject(bankReportContainer);
        } catch (IOException e) {
            e.getMessage();
        }

    }

    @Override
    public void printCommandInfo() {

    }
}
