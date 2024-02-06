package org.poo.cb;

import java.util.List;
import java.util.ArrayList;

public class Observer {

    Utilizator utilizator;
    public Observer(Utilizator utilizator) {
        this.utilizator = utilizator;
        this.utilizator.setObserver(this);
    }
    public void update() {
        System.out.println("Insufficient amount in account for buying stock");
        return;

    }

}