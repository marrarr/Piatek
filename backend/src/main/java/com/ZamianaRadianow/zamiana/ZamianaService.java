package com.ZamianaRadianow.zamiana;

import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import com.ZamianaRadianow.zamiana.model.Jednostka;
import com.ZamianaRadianow.zamiana.model.Zamiana;
import com.ZamianaRadianow.zamiana.transdata.StatystykiIloscPerUserAndJednostka;
import com.ZamianaRadianow.zamiana.transdata.StatystykiIloscPerUserTransData;
import com.ZamianaRadianow.zamiana.transdata.ZamianaTransDataOdpowiedz;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ZamianaService {
    private final Logger logger;
    private final ZamianaRepository zamianaRepository;
    private final UserRepository userRepository;


    public ZamianaService(Logger logger, ZamianaRepository zamianaRepository, UserRepository userRepository) {
        this.logger = logger;
        this.zamianaRepository = zamianaRepository;
        this.userRepository = userRepository;
    }


    public double konwertuj(double wartosc, Jednostka z, Jednostka na) {
        // zamiana wartości wejściowej na radiany
        double wartoscRadiany = switch (z) {
            case STOPNIE -> Math.toRadians(wartosc);
            case RADIANY -> wartosc;
        };

        // zamiana radianów na jednostkę docelową
        double wynik = switch (na) {
            case STOPNIE -> Math.toDegrees(wartoscRadiany);
            case RADIANY -> wartoscRadiany;
        };

        logger.info("Zamieniono " + wartosc + " " + z + " na " + wynik + " " + na);
        dodajRekord(wartosc, z, wynik, na);

        return wynik;
    }

    @Transactional
    public void dodajRekord(double wartosc, Jednostka z, double wynik, Jednostka na) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DBUser user = userRepository.findByUsername(username);

        Zamiana zamiana = new Zamiana(wartosc, z, wynik, na, user);
        zamianaRepository.save(zamiana);
        logger.info("Zapisano w bazie danych zamiane id=" + zamiana.getId());
    }

    @Transactional
    public void usunRekord(Long id) {
        Zamiana zamiana = zamianaRepository.findById(id).orElseThrow();
        zamianaRepository.delete(zamiana);
        logger.info("Usunięto z bazy danych zamiane id=" + zamiana.getId());
    }

    public ZamianaTransDataOdpowiedz zamienNaTransData(Zamiana zamiana) {
        return new ZamianaTransDataOdpowiedz(
                zamiana.getId(),
                zamiana.getWartoscWejsciowa(),
                zamiana.getJednostkaWejsciowa(),
                zamiana.getWartoscWynikowa(),
                zamiana.getJednostkaWynikowa(),
                zamiana.getCzasZamiany(),
                zamiana.getUser().getUsername()
        );
    }

    public List<ZamianaTransDataOdpowiedz> zamienNaTransData(List<Zamiana> zamiany) {
        return zamiany.stream().map(z -> zamienNaTransData(z)).toList();
    }

    public List<ZamianaTransDataOdpowiedz> getWszystkieZamiany() {
        List<Zamiana> zamiany = zamianaRepository.findAll();
        return zamienNaTransData(zamiany);
    }

    public List<ZamianaTransDataOdpowiedz> getWszystkieZamiany(String username) {
        List<Zamiana> zamiany = zamianaRepository.findAllByUser_Username(username);
        return zamienNaTransData(zamiany);
    }

    public List<StatystykiIloscPerUserTransData> getStatystykiIloscPerUser() {
        List<Object[]> wynik = zamianaRepository.countByZamianyPerUser();
        List<StatystykiIloscPerUserTransData> statystyki = new ArrayList<>();
        for (Object[] wiersz : wynik) {
            String username = (String) wiersz[0];
            long ilosc = (long) wiersz[1];
            statystyki.add(new StatystykiIloscPerUserTransData(username, ilosc));
        }
        return statystyki;
    }

    public List<StatystykiIloscPerUserAndJednostka> getStatystykiIloscPerUserAndJednostka() {
        List<Object[]> wynik1 = zamianaRepository.countZamianyByJednostka();
        List<Object[]> wynik2 = zamianaRepository.countZamianyByUzytkownikAndJednostka();
        List<StatystykiIloscPerUserAndJednostka> statystyki = new ArrayList<>();
        for (Object[] wiersz : wynik1) {
            String username = "null";
            Jednostka wejsciowa = (Jednostka) wiersz[0];
            Jednostka wynikowa = (Jednostka) wiersz[1];
            long ilosc = (long) wiersz[2];
            statystyki.add(new StatystykiIloscPerUserAndJednostka(username, wejsciowa, wynikowa, ilosc));
        }

        for (Object[] wiersz : wynik2) {
            String username = (String) wiersz[0];
            Jednostka wejsciowa = (Jednostka) wiersz[1];
            Jednostka wynikowa = (Jednostka) wiersz[2];
            long ilosc = (long) wiersz[3];
            statystyki.add(new StatystykiIloscPerUserAndJednostka(username, wejsciowa, wynikowa, ilosc));
        }

        return statystyki;
    }
}
