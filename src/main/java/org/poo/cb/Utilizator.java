package org.poo.cb;

import java.util.List;
import java.util.ArrayList;

public class Utilizator {

    private int state = 0;

    private final String nume;
    private final String email;
    private final String prenume;
    private final String adresa;

    private List<Utilizator> prieteni;
    private List<Accounts> accounts;
    private List<Stocks> stocks;
    private Factory factory = new Factory();

    public Observer observer = new Observer(this);
    private boolean isPremium = false;

    public int getState() {
        state = state + 1;
        return state;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    private void setState(int state) {
        this.state = state;
        notifyObserver();
    }

    public void notifyObserver() {
        observer.update();
    }

    public Utilizator(UtilizatorBuilder builder) {
        this.nume = builder.getNume();
        this.email = builder.getEmail();
        this.prenume = builder.getPrenume();
        this.adresa = builder.getAdresa();
        this.prieteni = builder.getPrieteni();
        this.accounts = builder.getAccounts();
        this.stocks = builder.getStocks();

    }

    public void addFriend(Utilizator utilizator) {
        prieteni.add(utilizator);
    }

    public void addAccount(String currency) {
        Accounts a = factory.createAccount(currency, this.email);
        accounts.add(a);
    }

    public String getEmail() {
        return email;
    }

    public List<Utilizator> getPrieteni() {
        return prieteni;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public List<Stocks> getStocks() {
        return stocks;
    }

    public void increaseBalance(String currency, double amount) {
        for (Accounts account : accounts) {
            if (account.getCurrency().equals(currency)) {
                account.increaseBalance(amount);
            }
        }
    }

    public void transferMoney(Utilizator utilizator, double amount1, int ct, String source, String destination, String[] data) {

        for (int i = 0; i < utilizator.getAccounts().size(); i++) {
            if (utilizator.getAccounts().get(i).getCurrency().equals(source)) {
                double amountIntermediar = amount1 * Double.parseDouble(data[ct]);
                if (utilizator.getAccounts().get(i).getBalance() < amountIntermediar) {
                    System.out.println("Insufficient amount in account " + source + " for exchange");
                    return;
                }
                if (utilizator.isPremium == true) {
                    utilizator.getAccounts().get(i).lowerBalance(amountIntermediar);
                } else if (utilizator.isPremium == false) {
                    if (utilizator.getAccounts().get(i).getBalance() / 2 > amountIntermediar) {
                        utilizator.getAccounts().get(i).lowerBalance(amountIntermediar);
                    } else {
                        utilizator.getAccounts().get(i).lowerBalance(amountIntermediar + 0.01 * amountIntermediar);
                    }
                }
            }
            if (utilizator.getAccounts().get(i).getCurrency().equals(destination)) {
                utilizator.getAccounts().get(i).modifyBalance(amount1);
            }
        }
    }

    public void exchange(Utilizator utilizator, String source, String destination, double amount1, String[] data) {

        if (source.equals("USD")) {
            transferMoney(utilizator, amount1, 5, source, destination, data);
        }

        if (source.equals("CAD")) {
            transferMoney(utilizator, amount1, 4, source, destination, data);
        }

        if (source.equals("JPY")) {
            transferMoney(utilizator, amount1, 3, source, destination, data);
        }

        if (source.equals("JPY")) {
            transferMoney(utilizator, amount1, 2, source, destination, data);
        }

        if (source.equals("EUR")) {
            transferMoney(utilizator, amount1, 1, source, destination, data);
        }
    }

    public boolean verifyFriendship(Utilizator utilizator2) {

        for (Utilizator utilizator : this.prieteni) {
            if (utilizator.getEmail().equals(utilizator2.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public void transferMoney(Utilizator destination, double amount, String currency) {

        for (int i = 0; i < this.getAccounts().size(); i++) {
            if (this.getAccounts().get(i).getCurrency().equals(currency)) {
                if (this.getAccounts().get(i).getBalance() < amount) {
                    System.out.println("Insufficient amount in account " + currency + " for transfer");
                    return;
                }
                this.getAccounts().get(i).lowerBalance(amount);
                for (int j = 0; j < destination.getAccounts().size(); j++) {
                    if (destination.getAccounts().get(j).getCurrency().equals(currency)) {
                        destination.getAccounts().get(j).increaseBalance(amount);
                    }
                }
            }
        }
    }


    public void makeTransaction(String stockName, double price, int amount, int i, int j, int ok) {
        if (accounts.get(j).getBalance() < price) {
            notifyObserver();
            return;
        }
        if (ok == 0) {
            accounts.get(j).lowerBalance(price);
            stocks.get(i).increaseAmount(amount);
            return;
        } else {
            accounts.get(j).lowerBalance(price);
            stocks.add(new Stocks(new StockBuilder().setAmount(amount).setName(stockName)));
            return;
        }
    }

    public void buyStock(String stockName, int amount, String price, List<String> stockuri) {
        double stockPrice = Double.parseDouble(price);
        int ok = 1;
        int checkStock = 0;
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getName().equals(stockName)) {
                for (int j = 0; j < accounts.size(); j++) {
                    if (accounts.get(j).getCurrency().equals("USD")) {
                        ok = 0;
                        checkStock = 0;
                        for (int k = 0; k < stockuri.size(); k++) {
                            if (stockuri.get(k).equals(stockName)) {
                                checkStock = 1;
                            }
                        }
                        if (this.isPremium == true && checkStock == 1) {
                            double discount = 0.95 * (stockPrice * amount);
                            makeTransaction(stockName, discount, amount, i, j, ok);
                        } else {
                            makeTransaction(stockName, stockPrice * amount, amount, i, j, ok);
                        }
                    }
                }
            }
        }
        if (ok == 1) {
            for (int j = 0; j < accounts.size(); j++) {
                if (accounts.get(j).getCurrency().equals("USD")) {
                    checkStock = 0;
                    for (int k = 0; k < stockuri.size(); k++) {
                        if (stockuri.get(k).equals(stockName)) {
                            checkStock = 1;
                        }
                    }
                    if (this.isPremium == true && checkStock == 1) {
                        double discount = 0.95 * (stockPrice * amount);
                        makeTransaction(stockName, discount, amount, 1, j, ok);
                        return;
                    } else {
                        makeTransaction(stockName, stockPrice * amount, amount, 1, j, ok);
                        return;
                    }
                }
            }
        }
    }

    public void buyPremium() {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getCurrency().equals("USD")) {
                if (accounts.get(i).getBalance() < 100) {
                    System.out.println("Insufficient amount in account for buying premium");
                    return;
                }
                accounts.get(i).lowerBalance(100);
                this.isPremium = true;
                return;
            }
        }
    }

}
