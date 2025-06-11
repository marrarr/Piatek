package com.ZamianaRadianow.zamiana.transdata;

import com.ZamianaRadianow.zamiana.model.Jednostka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ZamianaTransDataOdpowiedz {
    private Long id;
    private double wartoscWejsciowa;
    private Jednostka jednostkaWejsciowa;
    private double wartoscWynikowa;
    private Jednostka jednostkaWynikowa;
    private String czasZamiany;
    private String username; // tylko nazwa użytkownika

    public ZamianaTransDataOdpowiedz(Long id, double wartoscWejsciowa, Jednostka jednostkaWejsciowa, double wartoscWynikowa, Jednostka jednostkaWynikowa, LocalDateTime czasZamiany, String username) {
        this.id = id;
        this.wartoscWejsciowa = wartoscWejsciowa;
        this.jednostkaWejsciowa = jednostkaWejsciowa;
        this.wartoscWynikowa = wartoscWynikowa;
        this.jednostkaWynikowa = jednostkaWynikowa;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.czasZamiany = czasZamiany.format(formatter);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCzasZamiany() {
        return czasZamiany;
    }

    public void setCzasZamiany(String czasZamiany) {
        this.czasZamiany = czasZamiany;
    }

    public void setCzasZamiany(LocalDateTime czasZamiany) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.czasZamiany = czasZamiany.format(formatter);
    }

    public Jednostka getJednostkaWynikowa() {
        return jednostkaWynikowa;
    }

    public void setJednostkaWynikowa(Jednostka jednostkaWynikowa) {
        this.jednostkaWynikowa = jednostkaWynikowa;
    }

    public double getWartoscWynikowa() {
        return wartoscWynikowa;
    }

    public void setWartoscWynikowa(double wartoscWynikowa) {
        this.wartoscWynikowa = wartoscWynikowa;
    }

    public Jednostka getJednostkaWejsciowa() {
        return jednostkaWejsciowa;
    }

    public void setJednostkaWejsciowa(Jednostka jednostkaWejsciowa) {
        this.jednostkaWejsciowa = jednostkaWejsciowa;
    }

    public double getWartoscWejsciowa() {
        return wartoscWejsciowa;
    }

    public void setWartoscWejsciowa(double wartoscWejsciowa) {
        this.wartoscWejsciowa = wartoscWejsciowa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
