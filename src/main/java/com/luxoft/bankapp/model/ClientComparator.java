package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.Client;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Makarov Denis on 28.01.2015.
 */

    public class ClientComparator implements Comparator<Client>, Serializable {
        @Override
        public int compare(Client o1, Client o2) {
            int result = 0;
            if(o1.getActiveAccount()!=null && o2.getActiveAccount()!= null){
                if (o1.getActiveAccount().getBalance() > o2.getActiveAccount().getBalance()) {
                    result = 1;
                } else if (o1.getActiveAccount().getBalance() < o2.getActiveAccount().getBalance()) {
                    result = -1;
                }
            } else if(o1.getActiveAccount()==null && o2.getActiveAccount()==null) {
                result = 0;
            } else if(o1.getActiveAccount()==null){
                result = -1;
            } else{
                result = 1;
            }
            return result;
        }
    }