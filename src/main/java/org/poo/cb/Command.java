package org.poo.cb;

import java.io.FileReader;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Command {

    Database db;

    public Command(String rates, String stocks, String comands) {
        db = Database.getInstance();
    }

    public void create(String[] data) {

        String mail = data[2];
        String prenume = data[3];
        String nume = data[4];
        String adresa = "";
        for (int i = 5; i < data.length; i++) {
            if (i == data.length - 1) {
                adresa = adresa + data[i];
                break;
            }
            adresa = adresa + data[i] + " ";
        }

        for (int i = 0; i < db.utilizatori.size(); i++) {
            if (db.utilizatori.get(i).getEmail().equals(mail)) {
                System.out.println("User with " + mail + " already exists");
                return;
            }
        }

        Utilizator utilizator = new Utilizator(nume, mail, prenume, adresa);
        db.addUser(utilizator);

    }


    public void listUsers(String data) {
        Utilizator utilizator = db.getUtilizator(data);
        if (utilizator == null) {
            System.out.println("User with " + data + " doesn't exist");
            return;
        }
        System.out.print("{\"email\":\"" + utilizator.getEmail() + "\",\"firstname\":\"" + utilizator.getPrenume() + "\",\"lastname\":" + "\"" + utilizator.getNume() + "\"" + ",\"address\":" + "\"" + utilizator.getAdresa() + "\"" + ",\"friends\":[");
        if (utilizator.getPrieteni().size() == 0) {
            System.out.println("]}");
        } else {
            for (int i = 0; i < utilizator.getPrieteni().size() - 1; i++) {
                System.out.print("\"" + utilizator.getPrieteni().get(i).getEmail() + "\",");
            }
            System.out.println("\"" + utilizator.getPrieteni().get(utilizator.getPrieteni().size() - 1).getEmail() + "\"]}");
        }
    }

    public void listPortfolio(String name) {
        Utilizator utilizator = db.getUtilizator(name);
        if (utilizator == null) {
            System.out.println("User with " + name + " doesn't exist");
            return;
        }
        System.out.print("{\"stocks\":[");
        if (utilizator.getStocks().size() == 0) {
            System.out.print("]");
        } else {
            for (int i = 0; i < utilizator.getStocks().size() - 1; i++) {
                System.out.print("{\"stockName\":\"" + utilizator.getStocks().get(i).getName() + "\",\"amount\":" + utilizator.getStocks().get(i).getQuantity() + "},");
            }
            System.out.print("{\"stockName\":\"" + utilizator.getStocks().get(utilizator.getStocks().size() - 1).getName() + "\",\"amount\":" + utilizator.getStocks().get(utilizator.getStocks().size() - 1).getQuantity() + "}]");
        }
        System.out.print(",\"accounts\":[");
        if (utilizator.getAccounts().size() == 0) {
            System.out.println("]}");
        } else {
            for (int i = 0; i < utilizator.getAccounts().size() - 1; i++) {
                System.out.printf("{\"currencyname\":\"%s\",\"amount\":\"%.2f\"},", utilizator.getAccounts().get(i).getCurrency(), utilizator.getAccounts().get(i).getBalance());
            }
            System.out.printf("{\"currencyname\":\"%s\",\"amount\":\"%.2f\"}]}\n", utilizator.getAccounts().get(utilizator.getAccounts().size() - 1).getCurrency(), utilizator.getAccounts().get(utilizator.getAccounts().size() - 1).getBalance());
        }
    }

    public void addFriend(String data1, String data2) {

        if (db.getUtilizator(data1) == null) {
            System.out.println("User with email " + data1 + " does not exist");
            return;
        }
        if (db.getUtilizator(data2) == null) {
            System.out.println("User with email " + data2 + " does not exist");
            return;
        }
        Utilizator utilizator1 = db.getUtilizator(data1);

        for (Utilizator utilizator : utilizator1.getPrieteni()) {
            if (utilizator.getEmail().equals(data2)) {
                System.out.println("User with email " + data2 + " is already a friend");
                return;
            }
        }
        Utilizator utilizator2 = db.getUtilizator(data2);


        db.addFriend(utilizator1, utilizator2);

    }

    public void addAccount(String name, String currency) {

        Utilizator utilizator = db.getUtilizator(name);
        for (int i = 0; i < utilizator.getAccounts().size(); i++) {
            if (utilizator.getAccounts().get(i).getCurrency().equals(currency)) {
                System.out.println("Account in currency " + currency + " already exists for user");
                return;
            }
        }
        db.addAccount(name, currency);
    }

    public void addMoney(String name, String currency, String amount) {
        double amount1 = Double.parseDouble(amount);

        Utilizator utilizator = db.getUtilizator(name);
        for (int i = 0; i < utilizator.getAccounts().size(); i++) {
            if (utilizator.getAccounts().get(i).getCurrency().equals(currency)) {
                utilizator.increaseBalance(currency, amount1);
            }
        }
    }

    public void exchange(String email, String source, String destination, String amount) {
        double amount1 = Double.parseDouble(amount);
        Utilizator utilizator = db.getUtilizator(email);
        double sourceAmount = 0;
        double destinationAmount = 0;

        try {
            FileReader fr = new FileReader("./src/main/resources/common/exchangeRates.csv");
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");

                if (data[0].equals(destination)) {
                    utilizator.exchange(utilizator, source, destination, amount1, data);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void transfer(String source, String destination, String currency, String amount) {
        double amount1 = Double.parseDouble(amount);
        Utilizator utilizator1 = db.getUtilizator(source);
        Utilizator utilizator2 = db.getUtilizator(destination);
        boolean ok = utilizator1.verifyFriendship(utilizator2);
        if (ok == false) {
            System.out.println("You are not allowed to transfer money to " + destination);
            return;
        } else {
            utilizator1.transferMoney(utilizator2, amount1, currency);
        }

    }

    public void recommend(String file) {

        List<String> stocks = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            int count = 0;
            while (sc.hasNextLine()) {
                if (count == 0) {
                    count++;
                    sc.nextLine();
                    continue;
                }
                String line = sc.nextLine();
                String[] data = line.split(",");
                float price = 0;
                for (int i = 10; i >= 6; i--) {
                    price = price + Float.parseFloat(data[i]);
                }
                float average5 = price / 5;
                price = 0;
                for (int i = 10; i >= 1; i--) {
                    price = price + Float.parseFloat(data[i]);
                }
                float average10 = price / 10;

                if (average5 > average10) {
                    stocks.add(data[0]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.print("{\"stockstobuy\":[");
        if (stocks.size() == 0) {
            System.out.println("]}");
            return;
        } else {

            for (int i = 0; i < stocks.size() - 1; i++) {
                System.out.print("\"" + stocks.get(i) + "\",");
            }
            System.out.println("\"" + stocks.get(stocks.size() - 1) + "\"]}");
        }
    }

    public void buy(String email, String stock, String amount, String file) {
        Utilizator utilizator = db.getUtilizator(email);
        int amount1 = Integer.parseInt(amount);

        try {
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
                if (data[0].equals(stock)) {
                    utilizator.buyStock(stock, amount1, data[10]);
                    return;

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void end() {
        db.resetDb();
    }

}
