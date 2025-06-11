package com.ZamianaRadianow.zamiana.transdata;

public class StatystykiIloscPerUserTransData {
    private String username;
    private long ilosc;

    public StatystykiIloscPerUserTransData() {}

    public StatystykiIloscPerUserTransData(String username, long ilosc) {
        this.username = username;
        this.ilosc = ilosc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getIlosc() {
        return ilosc;
    }

    public void setIlosc(long ilosc) {
        this.ilosc = ilosc;
    }
}
