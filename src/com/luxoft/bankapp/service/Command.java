package com.luxoft.bankapp.service;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public interface Command {
    void execute();
    void printCommandInfo();
}
