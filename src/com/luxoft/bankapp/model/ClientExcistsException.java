package com.luxoft.bankapp.model;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class ClientExcistsException extends Exception {
    @Override
    public String getMessage(){
        return "Клиент с указанным именем уже существует";
    }

}
