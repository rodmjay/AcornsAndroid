package com.rodmjay.acorns;

/**
 * Created by Rod on 3/19/2015.
 */

public class AccountModel {

    public AccountModel(String firstName, String lastName, double accountBalance){
        this.AccountBalance = accountBalance;
        this.FirstName = firstName;
        this.LastName = lastName;
    }

    public double AccountBalance;
    public String FirstName;
    public String LastName;
}
