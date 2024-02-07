package org.poo.cb;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Running Main");
        } else {
            final String path = "src/main/resources/";

            String[] files = Arrays.stream(args).map(file -> path + file).toArray(String[]::new);

            Command comenzi = new Command(files[0], files[1],files[2]);

            try {
                FileReader fr = new FileReader(files[2]);
                Scanner sc = new Scanner(fr);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();

                    String[] data = line.split(" ");

                    if (data[0].equals("CREATE")) {
                        comenzi.create(data[2],data[3],data[4],data);
                    }
                    if(data[0].equals("LIST")) {
                        if (data[1].equals("USER")) {
                            comenzi.listUsers(data[2]);
                        }
                        if (data[1].equals("PORTFOLIO")) {
                            comenzi.listPortfolio(data[2]);
                        }
                    }
                    if(data[0].equals("ADD")) {

                        if (data[1].equals("FRIEND")) {
                            comenzi.addFriend(data[2],data[3]);
                        }
                        else if (data[1].equals("ACCOUNT")) {
                            comenzi.addAccount(data[2],data[3]);
                        }
                        else if (data[1].equals("MONEY")) {
                            comenzi.addMoney(data[2],data[3],data[4]);
                        }
                    }
                    if (data[0].equals("EXCHANGE")) {
                        comenzi.exchange(data[2],data[3],data[4],data[5]);
                    }
                    if (data[0].equals("TRANSFER")) {
                        comenzi.transfer(data[2],data[3],data[4],data[5]);
                    }
                    if (data[0].equals("RECOMMEND")) {
                        comenzi.recommend(files[1]);
                    }
                    if(data[0].equals("BUY")) {
                        if (data[1].equals("STOCKS"))
                            comenzi.buy(data[2],data[3],data[4],files[1]);
                        if (data[1].equals("PREMIUM")) {
                            comenzi.buyPremium(data[2]);
                        }

                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            comenzi.end();
        }
    }
}
