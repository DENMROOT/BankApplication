package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by SCJP on 14.01.2015.
 */
public class Client implements Report, Comparable <Client>, Serializable{


    private String name;
    private float initialOverdraft;
    private Set<Account> accounts = new HashSet<Account>();
    Account activeAccount;
    Gender gender;
    private String city;
    private String email;
    private String phone;

    public Client (Gender gender) {
        this.gender=gender;
    }

    public Client() {

    }

    @Override
    public boolean equals(Object obj) {

        Client equalsClient = (Client) obj;
        //System.out.println("изначальный объект" + this.getName());
        //System.out.println("сравниваемый объект" + equalsClient.getName());

        if (this == obj) return true;
        if (obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;

        if (this.getName().equals(equalsClient.getName()) && this.gender == equalsClient.gender) return true;

        return false;
    }

    @Override
    public String toString() {
        StringBuilder clientToStringInfo = new StringBuilder();
        StringBuilder accountInfo = new StringBuilder();

        for (Account accountIterator : accounts){
            accountInfo.append(accountIterator.toString());
            accountInfo.append("\n");
        }
        clientToStringInfo.append("Client{").append("name='").append(name).append(", gender=").append(getClientSalutation()).append("}").append("\n").append(accountInfo);
        return clientToStringInfo.toString();
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

    @Override
    public void printReport() {
        for (Account accountItem : getAccounts()) {
            System.out.println(" Balance="+accountItem.getBalance()+" Overdraft="+accountItem.getOverdraft());
        }

        System.out.println("Активный счет :" + " Balance="+activeAccount.getBalance()+" Overdraft="+activeAccount.getOverdraft());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void setActiveAccount(Account account) {
        activeAccount=account;
    }

    public String getClientSalutation() {
        return this.gender.getSalutation();
    }

    public void withdrawFromAccount (Account account, float withdrowalSum) throws NotEnoughFundsException, OverDraftLimitExceededException{
        account.withdraw(withdrowalSum);
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    @Override
    public int compareTo(Client o) {
        return this.name.compareTo(o.name);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Account createAccount(String type, String valueBalance, String valueOverdraft){
        Account account;
        switch(type){
            case "c" : account = new CheckingAccount(Float.valueOf(valueOverdraft));
                break;
            case "s" : account = new SavingAccount(Float.valueOf(valueBalance));
                break;
            default : throw new IllegalArgumentException();
        }
        return account;
    }

    public void parseFeed (Map<String,String> feed) {
        /*
        Задаем параметры счета Email, Phone, City
         */
        setEmail(feed.get("email"));
        setPhone(feed.get("phone"));
        setCity(feed.get("city"));

        /*
        Устанавливаем начальный овердрафт по клиенту
        СОздаем и делаем текушим активным новый счет по клиенту
         */
        setInitialOverdraft(Float.parseFloat(feed.get("overdraft")));
        Account account = createAccount(feed.get("accounttype"), feed.get("balance"), feed.get("overdraft") );
        account.parseFeed(feed);
        setActiveAccount(account);
        addAccount(account);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }
}
