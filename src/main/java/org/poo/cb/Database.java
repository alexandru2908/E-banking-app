package org.poo.cb;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.ArrayList;

public class Database {

    private  static Database instance = null;

    List<Utilizator> utilizatori;

    public void addUser(Utilizator utilizator) {
        utilizatori.add(utilizator);
    }

    private Database() {
        utilizatori = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Utilizator getUtilizator(String email) {
        for (Utilizator utilizator : utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                return utilizator;
            }
        }
        return null;
    }

    public void addFriend(Utilizator utilizator1, Utilizator utilizator2) {
        for (Utilizator utilizator : utilizatori) {
            if (utilizator.getEmail().equals(utilizator1.getEmail())) {
                utilizator1.addFriend(utilizator2);
            }

            if (utilizator.getEmail().equals(utilizator2.getEmail())) {
                utilizator2.addFriend(utilizator1);
            }

        }
    }

    public void addAccount(String name, String currency) {
        for (Utilizator user : utilizatori) {
            if (user.getEmail().equals(name)) {
                user.addAccount(currency);
            }
        }

    }

    public void resetDb() {
        instance = null;
    }


}
