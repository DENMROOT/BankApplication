package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.Client;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public interface ClientRegistrationListener {
    void onClientAdded(Client c);
}
