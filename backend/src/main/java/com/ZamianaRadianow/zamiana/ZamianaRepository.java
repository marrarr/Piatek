package com.ZamianaRadianow.zamiana;

import com.ZamianaRadianow.zamiana.model.Zamiana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZamianaRepository extends JpaRepository<Zamiana, Long> {
    // wszystkie zamiany konkretnego użytkownika (do historii)
    List<Zamiana> findAllByUser_Username(String username);

    // ilość zamian każdego użytkownika (do statystyk)
    @Query("""
        SELECT u.username, count(z)
        FROM Zamiana z
        JOIN z.user u
        GROUP BY u.username
    """)
    List<Object[]> countByZamianyPerUser();

    // ilosc kazdej kombinacji zamian (dwa zapytania, bo nie ma grouping sets)
    @Query("""
        SELECT u.username, z.jednostkaWejsciowa, z.jednostkaWynikowa, COUNT(z)
        FROM Zamiana z
        JOIN z.user u
        GROUP BY u.username, z.jednostkaWejsciowa, z.jednostkaWynikowa
    """)
    List<Object[]> countZamianyByUzytkownikAndJednostka();

    @Query("""
        SELECT z.jednostkaWejsciowa, z.jednostkaWynikowa, COUNT(z)
        FROM Zamiana z
        GROUP BY z.jednostkaWejsciowa, z.jednostkaWynikowa
    """)
    List<Object[]> countZamianyByJednostka();
}