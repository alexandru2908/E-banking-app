
package org.poo.cb;
import java.util.List;
import java.util.ArrayList;
public class UtilizatorBuilder {


    private String nume;
    private String email;
    private String prenume;
    private String adresa;

    private List<Utilizator> prieteni;
    private List<Accounts> accounts;
    private List<Stocks> stocks;



    public String getNume() {
        return this.nume;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPrenume() {
        return this.prenume;
    }
    public String getAdresa() {
        return this.adresa;
    }
    public List<Utilizator> getPrieteni() {
        return new ArrayList<>();
    }
    public List<Accounts> getAccounts() {
        return new ArrayList<>();
    }
    public List<Stocks> getStocks() {
        return new ArrayList<>();
    }

    public UtilizatorBuilder setNume(String nume) {
        this.nume = nume;
        return this;
    }
    public UtilizatorBuilder setEmail(String email) {
        this.email = email;
        return this;
    }
    public UtilizatorBuilder setPrenume(String prenume) {
        this.prenume = prenume;
        return this;
    }
    public UtilizatorBuilder setAdresa(String adresa) {
        this.adresa = adresa;
        return this;
    }
    public UtilizatorBuilder setPrieteni(List<Utilizator> prieteni) {
        this.prieteni = prieteni;
        return this;
    }
    public UtilizatorBuilder setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
        return this;
    }
    public UtilizatorBuilder setStocks(List<Stocks> stocks) {
        this.stocks = stocks;
        return this;
    }




}