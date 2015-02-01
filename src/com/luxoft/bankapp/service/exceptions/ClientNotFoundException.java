package com.luxoft.bankapp.service.exceptions;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class ClientNotFoundException extends Exception {
    @Override
    public String getMessage(){
        return "Клиент с указанным именем не найден";
    }
}
