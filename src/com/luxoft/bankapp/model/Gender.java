package com.luxoft.bankapp.model;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public enum Gender {
    MALE("Mr."),FEMALE("Mrs.");

    private final String salutation;

    Gender(String salutation){
        this.salutation = salutation;
    }

    public String getSalutation (){
        return salutation;
    }
}
