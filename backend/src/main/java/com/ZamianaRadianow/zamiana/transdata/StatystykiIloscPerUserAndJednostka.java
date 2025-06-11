package com.ZamianaRadianow.zamiana.transdata;

import com.ZamianaRadianow.zamiana.model.Jednostka;

public class StatystykiIloscPerUserAndJednostka {
    public StatystykiIloscPerUserAndJednostka(String username, Jednostka jednostkaWejsciowa, Jednostka jednostkaWynikowa, long ilosc) {
        this.username = username;
        this.jednostkaWejsciowa = jednostkaWejsciowa;
        this.jednostkaWynikowa = jednostkaWynikowa;
        this.ilosc = ilosc;
    }

    private String username;
    private Jednostka jednostkaWejsciowa;
    private Jednostka jednostkaWynikowa;
    private long ilosc;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Jednostka getJednostkaWejsciowa() {
        return jednostkaWejsciowa;
    }

    public void setJednostkaWejsciowa(Jednostka jednostkaWejsciowa) {
        this.jednostkaWejsciowa = jednostkaWejsciowa;
    }

    public Jednostka getJednostkaWynikowa() {
        return jednostkaWynikowa;
    }

    public void setJednostkaWynikowa(Jednostka jednostkaWynikowa) {
        this.jednostkaWynikowa = jednostkaWynikowa;
    }

    public long getIlosc() {
        return ilosc;
    }

    public void setIlosc(long ilosc) {
        this.ilosc = ilosc;
    }
}
