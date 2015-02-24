package com.luxoft.bankapp.service.clientServer.clientCommands;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public interface ClientCommand {
    void execute(DataOutputStream out, Socket client);
    void printCommandInfo();
}
