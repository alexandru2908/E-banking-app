package org.poo.cb;
import java.util.List;
import java.util.ArrayList;


public class StockBuilder {

    private String name;
    private int quantity;

    public String getName() {
        return this.name;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public StockBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public StockBuilder setAmount(int quantity) {
        this.quantity = quantity;
        return this;
    }


}