package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.annotations.NoDB;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.exceptions.OverDraftLimitExceededException;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class Client implements Report, Comparable <Client>, Serializable, BaseClassMarkerInterface{
    @NoDB private long clientID;
    private String name;
    @NoDB private float initialOverdraft;
    private Set<Account> accounts = new HashSet<Account>();
    @NoDB private Account activeAccount;
    private Gender gender;
    private String city;
    private String email;
    private String phone;

    public Client (Gender gender) {
        this.setGender(gender);
    }

    public Client() {

    }

    @Override
    public boolean equals(Object obj) {

        Client equalsClient = (Client) obj;

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }

        if ((this.name == null) && equalsClient.name != null) {
            if ("".equals(equalsClient.name)) {
                return true;
            }
            else {
                return false;
            }
        }

        if ((this.name == null) && equalsClient.name == null) {
            if (this.gender == equalsClient.gender) {
                return true;
            } else return false;
        }

        if (this.name.equals(equalsClient.name) && this.gender == equalsClient.gender) {
            return true;
        }

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
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public void printReport() {
        for (Account accountItem : getAccounts()) {
            System.out.println(" Balance="+accountItem.getBalance()+" Overdraft="+accountItem.getOverdraft());
        }

        System.out.println("Активный счет :" + " Balance="+ getActiveAccount().getBalance()+" Overdraft="+ getActiveAccount().getOverdraft());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Pattern p = Pattern.compile("^[a-zA-Zа-яА-Я ]+$");
        Matcher m = p.matcher(name);
        if (m.matches()) {this.name = name;}
        else throw new IllegalArgumentException("Имя пользователя задано неверно");

    }

    public void addAccount(Account account) {
        accounts.add(account);
        setActiveAccount(account);
    }

    public void setActiveAccount(Account account) {
        activeAccount=account;
    }

    public String getClientSalutation() {
        return this.getGender().getSalutation();
    }

    public void withdrawFromAccount (Account account, float withdrowalSum) throws NotEnoughFundsException, OverDraftLimitExceededException {
        account.withdraw(withdrowalSum);
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    @Override
    public int compareTo(Client o) {
        return this.name.compareTo(o.name);
    }

    public void depositToAccount(Account account, float depositSum) {
        account.deposit(depositSum);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        Pattern p = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
        Matcher m = p.matcher(city);
        if (m.matches()) {this.city = city;}
        else throw new IllegalArgumentException("Город задан неверно");
    }

    public Account createAccount(String accounttype, String accountID, String valueBalance, String valueOverdraft){
        Account account;
        switch(accounttype){
            case "c" : account = new CheckingAccount(Long.valueOf(accountID),Float.valueOf(valueBalance),Float.valueOf(valueOverdraft));
                break;
            case "s" : account = new SavingAccount(Long.valueOf(accountID),Float.valueOf(valueBalance));
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
        Account account = createAccount(feed.get("accounttype"), feed.get("accountid"),feed.get("balance"), feed.get("overdraft") );
        account.parseFeed(feed);
        setActiveAccount(account);
        addAccount(account);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Pattern p = Pattern.compile("^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$");
        Matcher m = p.matcher(email);
        if (m.matches()) {this.email = email;}
        else throw new IllegalArgumentException("Email задан неверно");

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        Pattern p = Pattern.compile("^\\([0-9]{3}\\)[0-9]{7}$");
        Matcher m = p.matcher(phone);
        if (m.matches()) {this.phone = phone;}
        else throw new IllegalArgumentException("Телефон задан неверно");

    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public float getBalance () {
        float currentBalance = 0;
        for (Account accountIterator : accounts){
            currentBalance += accountIterator.getBalance();
        }
        return currentBalance;
    }
}
