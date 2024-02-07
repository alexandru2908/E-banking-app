package org.poo.cb;


public class Stocks {

    private final String name;
    int quantity;

    public Stocks(StockBuilder builder) {
        this.name = builder.getName();
        this.quantity = builder.getQuantity();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
    public void increaseAmount(int amount) {
        this.quantity += amount;
    }



}