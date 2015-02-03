package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.annotations.NoDB;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class Bank implements Report,BaseClassMarkerInterface {

    @NoDB
    private long bankID;

    private String name;

    private Set<Client> clients = new HashSet<Client>();

    @NoDB
    List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();

    @NoDB
    Map <String, Client> clientsMap = new HashMap<String, Client>();

    public Bank(List<ClientRegistrationListener> listenersList){
        this();
        this.listeners.addAll(listenersList);
    }

    public Bank(){
        listeners.add(new ClientRegistrationListener(){
            @Override
            public void onClientAdded(Client client) {
                Date curDate = new Date();
                String curStringDate = new SimpleDateFormat("HH:mm:ss").format(curDate);
                System.out.println(client.getName()+" время - " + curStringDate);
                clientsMap.put(client.getName(),client);

            }
        });
    }

    public long getBankID() {
        return bankID;
    }

    public void setBankID(long bankID) {
        this.bankID = bankID;
    }


    public static class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {

            System.out.println("Клиент создан");
            System.out.println(client.getClientSalutation() + " " + client.getName());
        }
    }

    public static class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {
            System.out.println("Мейл отправлен");
            System.out.println("Notification email for client " + client.getName() + " to be sent");
        }
    }

    @Override
    public void printReport() {
        for (Client listItem : clients) {
            System.out.println(listItem.getClientSalutation() + " " + listItem.getName());
            listItem.printReport();
        }
    }

    public Set<Client> getClients() {
        return Collections.unmodifiableSet (clients);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addClient(Client client) throws ClientExcistsException {

        if (containsClientAllready(client)) {
            throw new ClientExcistsException();
        }

        clients.add(client);
        for (ClientRegistrationListener iterator : listeners) {

            iterator.onClientAdded(client);
        }
    }


    public boolean containsClientAllready(Client client) {
        return clients.contains(client);
    }

    public Client findClient (String clientName){


        for (Client clientIterator : clients) {
            if (clientIterator.getName().equals(clientName)) {
                return clientIterator;
            }
        }

        return null;
    }

    public void parseFeed (Map<String,String> feed) {
        String name = feed.get("name"); // client name
            // try to find client by his name
            Client client = findClient(name);
            if (client == null) { // if no client then create it
                switch (feed.get("gender")) {
                    case "m":
                        client = new Client(Gender.MALE);
                        break;
                    case "f":
                        client = new Client(Gender.FEMALE);
                        break;
                    default:
                        System.out.println("Некорректно задан пол клиента");

                }
                try {client.setName(name);}
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    addClient(client);
                } catch (ClientExcistsException e) {
                    System.out.println("Ошибка создания клиента при парсинге");
                    ;
                }
            }
            client.parseFeed(feed);
    }
}
