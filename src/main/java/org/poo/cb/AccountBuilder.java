package org.poo.cb;
import java.util.List;
import java.util.ArrayList;

public class AccountBuilder {

    private String currency;
    private String email;

    public String getCurrency() {
        return this.currency;
    }

    public String getEmail() {
        return this.email;
    }

    public AccountBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public AccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }
}