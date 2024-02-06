package org.poo.cb;

import java.util.List;
import java.util.ArrayList;

public class Utilizator {

    private String nume;
    private String email;
    private String prenume;
    private String adresa;

    private List<Utilizator> prieteni;
    private List<Accounts> accounts;
    private List<Stocks> stocks;


    public Utilizator(String nume, String email, String prenume, String adresa) {
        this.nume = nume;
        this.email = email;
        this.prenume = prenume;
        this.adresa = adresa;
        this.prieteni = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.stocks = new ArrayList<>();
    }

    public void addFriend(Utilizator utilizator) {
        prieteni.add(utilizator);
    }

    public void addAccount(String currency) {
        accounts.add(new Accounts(currency, this.email));
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
                if (utilizator.getAccounts().get(i).getBalance() / 2 > amountIntermediar) {
                    utilizator.getAccounts().get(i).lowerBalance(amountIntermediar);
                } else {
                    utilizator.getAccounts().get(i).lowerBalance(amountIntermediar + 0.01 * amountIntermediar);
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

    public boolean verifyFriendship( Utilizator utilizator2) {
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

    public void buyStock(String stockName, int amount, String price) {
        double stockPrice = Double.parseDouble(price);
        int ok = 1;
        for (int i = 0; i< stocks.size(); i++) {
            if (stocks.get(i).getName().equals(stockName)) {
                for (int j = 0; j < accounts.size(); j++) {
                    if (accounts.get(j).getCurrency().equals("USD")) {
                        ok = 0;
                        if (accounts.get(j).getBalance() < stockPrice * amount) {
                            System.out.println("Insufficient amount in account for buying stock");
                            return;
                        }
                        accounts.get(j).lowerBalance(stockPrice * amount);
                        System.out.print(stockPrice * amount);
                        return;
                    }
                }
            }
        }
        if (ok == 1) {
            for (int j = 0; j < accounts.size(); j++) {
                if (accounts.get(j).getCurrency().equals("USD")) {
                    if (accounts.get(j).getBalance() < stockPrice * amount) {
                        System.out.println("Insufficient amount in account for buying stock");
                        return;
                    }
                    accounts.get(j).lowerBalance(stockPrice * amount);
                    stocks.add(new Stocks(stockName, amount));
                    return;
                }
            }
        }

    }
}
