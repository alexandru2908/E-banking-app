package org.poo.cb;
public class Accounts {
    private double balance;

    private String name;
    private String currency;

//    public Accounts(String currency, String name) {
//        this.currency = currency;
//        this.name = name;
//        this.balance = 0.00;
//    }

    public Accounts(AccountBuilder builder) {
        this.currency = builder.getCurrency();
        this.name = builder.getEmail();
        this.balance = 0.00;
    }

    public String  getCurrency() {
        return this.currency;
    }
    public String getName() {
        return this.name;
    }
    public double getBalance() {
        return this.balance;
    }

    public void increaseBalance(double amount) {
        this.balance += amount;
    }
    public void modifyBalance(double amount) {
        this.balance += amount;
    }
    public void lowerBalance(double amount) {
        this.balance -= amount;
    }

}