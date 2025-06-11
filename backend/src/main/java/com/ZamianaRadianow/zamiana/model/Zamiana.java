package com.ZamianaRadianow.zamiana.model;

import com.ZamianaRadianow.security.user.DBUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class Zamiana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double wartoscWejsciowa;

    @Enumerated(EnumType.STRING)
    private Jednostka jednostkaWejsciowa;

    private double wartoscWynikowa;

    @Enumerated(EnumType.STRING)
    private Jednostka jednostkaWynikowa;

    @CurrentTimestamp
    private LocalDateTime czasZamiany;

    public void setUser(DBUser user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DBUser user;


    public Zamiana() {}

    public Zamiana(double wartoscWejsciowa, Jednostka jednostkaWejsciowa, double wartoscWynikowa, Jednostka jednostkaWynikowa, DBUser user) {
        this.wartoscWejsciowa = wartoscWejsciowa;
        this.jednostkaWejsciowa = jednostkaWejsciowa;
        this.wartoscWynikowa = wartoscWynikowa;
        this.jednostkaWynikowa = jednostkaWynikowa;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Zamiana{" +
                "id=" + id +
                ", wartoscWejsciowa=" + wartoscWejsciowa +
                ", jednostkaWejsciowa=" + jednostkaWejsciowa +
                ", wartoscWynikowa=" + wartoscWynikowa +
                ", jednostkaWynikowa=" + jednostkaWynikowa +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getWartoscWynikowa() {
        return wartoscWynikowa;
    }

    public void setWartoscWynikowa(double wartoscWynikowa) {
        this.wartoscWynikowa = wartoscWynikowa;
    }

    public Jednostka getJednostkaWynikowa() {
        return jednostkaWynikowa;
    }

    public void setJednostkaWynikowa(Jednostka jednostkaWynikowa) {
        this.jednostkaWynikowa = jednostkaWynikowa;
    }

    public LocalDateTime getCzasZamiany() {
        return czasZamiany;
    }

    public void setCzasZamiany(LocalDateTime czasZamiany) {
        this.czasZamiany = czasZamiany;
    }

    public DBUser getUser() {
        return user;
    }
}
