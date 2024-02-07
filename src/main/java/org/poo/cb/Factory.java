package org.poo.cb;


public class Factory {

    public Accounts createAccount(String currency, String name) {

        if (currency.equals("EUR")) {
            return new Accounts(new AccountBuilder().setCurrency("EUR").setEmail(name));
        }
        if (currency.equals("USD")) {
            return new Accounts(new AccountBuilder().setCurrency("USD").setEmail(name));
        }
        if (currency.equals("JPY")) {
            return new Accounts(new AccountBuilder().setCurrency("JPY").setEmail(name));
        }
        if (currency.equals("CAD")) {
            return new Accounts(new AccountBuilder().setCurrency("CAD").setEmail(name));
        }
        if (currency.equals("GBP")) {
            return new Accounts(new AccountBuilder().setCurrency("GBP").setEmail(name));
        }
        return null;

    }
}
