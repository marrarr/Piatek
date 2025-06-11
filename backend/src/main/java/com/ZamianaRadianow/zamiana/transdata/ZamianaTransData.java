package com.ZamianaRadianow.zamiana.transdata;

import com.ZamianaRadianow.zamiana.model.Jednostka;


public class ZamianaTransData {
    private double wartoscWejsciowa;
    private Jednostka jednostkaWejsciowa;
    private Jednostka jednostkaWynikowa;

    public double getWartoscWejsciowa() {
        return wartoscWejsciowa;
    }

    public void setWartoscWejsciowa(double wartoscWejsciowa) {
        this.wartoscWejsciowa = wartoscWejsciowa;
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
}
