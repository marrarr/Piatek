package com.ZamianaRadianow.zamiana;

import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import com.ZamianaRadianow.zamiana.model.Jednostka;
import com.ZamianaRadianow.zamiana.model.Zamiana;
import com.ZamianaRadianow.zamiana.transdata.ZamianaTransDataOdpowiedz;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZamianaServiceTest {
    @Mock
    private ZamianaRepository zamianaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Logger logger;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ZamianaService zamianaService;

    @BeforeAll
    public static void init() {
        System.out.println(">>>Przed wszystkimi testami.");
    }

    @AfterAll
    public static void finish() {
        System.out.println("<<<Po wszystkich testach.");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("+++Przed testem");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("---Po tescie");
    }

    @Test
    void testKonwertuj_powinnoPoprawniePrzekonwertowac() {
        // Test konwersji stopni na radiany
        double wynik = zamianaService.konwertuj(90, Jednostka.STOPNIE, Jednostka.RADIANY);
        assertEquals(Math.PI/2, wynik, 0.0001);

        // Test konwersji radianów na stopnie
        wynik = zamianaService.konwertuj(Math.PI/2, Jednostka.RADIANY, Jednostka.STOPNIE);
        assertEquals(90, wynik, 0.0001);

        // Test konwersji stopni na stopnie
        wynik = zamianaService.konwertuj(45, Jednostka.STOPNIE, Jednostka.STOPNIE);
        assertEquals(45, wynik, 0.0001);

        // Test konwersji radianów na radiany
        wynik = zamianaService.konwertuj(1.5, Jednostka.RADIANY, Jednostka.RADIANY);
        assertEquals(1.5, wynik, 0.0001);
    }

    @Test
    void testDodajRekord_powinnoPoprawnieDodacRekord() {
        double watosc = 90.0;
        double wynik = Math.PI / 2;
        Jednostka z = Jednostka.STOPNIE;
        Jednostka na = Jednostka.RADIANY;

        DBUser mockUser = new DBUser();
        mockUser.setUsername("user2");


        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user2");
        when(userRepository.findByUsername("user2")).thenReturn(mockUser);


        zamianaService.dodajRekord(watosc, z, wynik, na);

        verify(zamianaRepository, times(1)).save(any(Zamiana.class));
    }

    @Test
    void testUsunRekord_powinnoPoprawnieUsunacJedenRekord() {
        Long idDoUsuniecia = 5L;
        Zamiana zamianaMock = mock(Zamiana.class);

        when(zamianaRepository.findById(idDoUsuniecia)).thenReturn(Optional.of(zamianaMock));

        zamianaService.usunRekord(idDoUsuniecia);

        verify(zamianaRepository, times(1)).delete(any(Zamiana.class));
    }

    @Test
    void testUsunRekord_powinnoRzucicWyjatekBrakDanegoRekordu() {
        Long idDoUsuniecia = 5L;

        when(zamianaRepository.findById(idDoUsuniecia)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            zamianaService.usunRekord(idDoUsuniecia);
        });

        // delete nie powinno być wywołane
        verify(zamianaRepository, never()).delete(any(Zamiana.class));
    }

    @Test
    void testZamienNaTransData_powinnoDobrzeZmappowac() {
        Zamiana zamianaMock = new Zamiana();
        zamianaMock.setWartoscWejsciowa(90.0);
        zamianaMock.setJednostkaWejsciowa(Jednostka.STOPNIE);
        zamianaMock.setWartoscWynikowa(Math.PI / 2);
        zamianaMock.setJednostkaWynikowa(Jednostka.RADIANY);
        zamianaMock.setCzasZamiany(LocalDateTime.now());

        DBUser mockUser = new DBUser();
        mockUser.setUsername("user2");
        zamianaMock.setUser(mockUser);

        ZamianaTransDataOdpowiedz transData = zamianaService.zamienNaTransData(zamianaMock);

        assertNotNull(transData);
        assertEquals(90.0, transData.getWartoscWejsciowa());
        assertEquals(Jednostka.STOPNIE, transData.getJednostkaWejsciowa());
        assertEquals(Math.PI / 2, transData.getWartoscWynikowa(), 0.0001);
        assertEquals(Jednostka.RADIANY, transData.getJednostkaWynikowa());
        assertEquals("user2", transData.getUsername());
    }

    @Test
    void testZamienNaTransData_powinnoPoprawnieZmappowacListe() {
        Zamiana zamianaMock1 = new Zamiana();
        zamianaMock1.setWartoscWejsciowa(90.0);
        zamianaMock1.setJednostkaWejsciowa(Jednostka.STOPNIE);
        zamianaMock1.setWartoscWynikowa(Math.PI / 2);
        zamianaMock1.setJednostkaWynikowa(Jednostka.RADIANY);
        DBUser mockUser = new DBUser();
        mockUser.setUsername("user2");
        zamianaMock1.setUser(mockUser);
        zamianaMock1.setCzasZamiany(LocalDateTime.now());

        Zamiana zamianaMock2 = new Zamiana();
        zamianaMock2.setWartoscWejsciowa(Math.PI);
        zamianaMock2.setJednostkaWejsciowa(Jednostka.RADIANY);
        zamianaMock2.setWartoscWynikowa(180.0);
        zamianaMock2.setJednostkaWynikowa(Jednostka.STOPNIE);
        DBUser mockUser2 = new DBUser();
        mockUser2.setUsername("user20");
        zamianaMock2.setUser(mockUser2);
        zamianaMock2.setCzasZamiany(LocalDateTime.now());

        List<Zamiana> zamianyLista = new ArrayList<>();
        zamianyLista.add(zamianaMock1);
        zamianyLista.add(zamianaMock2);


        List<ZamianaTransDataOdpowiedz> transData = zamianaService.zamienNaTransData(zamianyLista);


        assertNotNull(transData);
        assertEquals(2, transData.size());
        // zamiana 1
        assertEquals(90.0, transData.get(0).getWartoscWejsciowa());
        assertEquals(Jednostka.STOPNIE, transData.get(0).getJednostkaWejsciowa());
        assertEquals(Math.PI / 2, transData.get(0).getWartoscWynikowa(), 0.0001);
        assertEquals(Jednostka.RADIANY, transData.get(0).getJednostkaWynikowa());
        assertEquals("user2", transData.get(0).getUsername());
        // zamiana 2
        assertEquals(Math.PI, transData.get(1).getWartoscWejsciowa());
        assertEquals(Jednostka.RADIANY, transData.get(1).getJednostkaWejsciowa());
        assertEquals(180.0, transData.get(1).getWartoscWynikowa(), 0.0001);
        assertEquals(Jednostka.STOPNIE, transData.get(1).getJednostkaWynikowa());
        assertEquals("user20", transData.get(1).getUsername());
    }

    @Test
    void testGetWszystkieZamiany_powinnoZwrocicWszystkieElementyJedenRaz() {
        Zamiana zamianaMock1 = new Zamiana();
        zamianaMock1.setWartoscWejsciowa(90.0);
        zamianaMock1.setJednostkaWejsciowa(Jednostka.STOPNIE);
        zamianaMock1.setWartoscWynikowa(Math.PI / 2);
        zamianaMock1.setJednostkaWynikowa(Jednostka.RADIANY);
        DBUser mockUser = new DBUser();
        mockUser.setUsername("user2");
        zamianaMock1.setUser(mockUser);
        zamianaMock1.setCzasZamiany(LocalDateTime.now());

        Zamiana zamianaMock2 = new Zamiana();
        zamianaMock2.setWartoscWejsciowa(Math.PI);
        zamianaMock2.setJednostkaWejsciowa(Jednostka.RADIANY);
        zamianaMock2.setWartoscWynikowa(180.0);
        zamianaMock2.setJednostkaWynikowa(Jednostka.STOPNIE);
        DBUser mockUser2 = new DBUser();
        mockUser2.setUsername("user20");
        zamianaMock2.setUser(mockUser2);
        zamianaMock2.setCzasZamiany(LocalDateTime.now());

        List<Zamiana> zamianyLista = new ArrayList<>();
        zamianyLista.add(zamianaMock1);
        zamianyLista.add(zamianaMock2);


        when(zamianaRepository.findAll()).thenReturn(zamianyLista);


        List<ZamianaTransDataOdpowiedz> transData = zamianaService.getWszystkieZamiany();


        assertEquals(2, transData.size());
        assertEquals(Math.PI / 2, transData.get(0).getWartoscWynikowa());
        assertEquals(180.0, transData.get(1).getWartoscWynikowa());
        verify(zamianaRepository, times(1)).findAll();
    }

    @Test
    void testGetWszystkieZamiany_powinnoZwrocicWszystkieElementyJedenRaz_dlaKonkretnegoUsername() {
        Zamiana zamianaMock1 = new Zamiana();
        zamianaMock1.setWartoscWejsciowa(90.0);
        zamianaMock1.setJednostkaWejsciowa(Jednostka.STOPNIE);
        zamianaMock1.setWartoscWynikowa(Math.PI / 2);
        zamianaMock1.setJednostkaWynikowa(Jednostka.RADIANY);
        DBUser mockUser = new DBUser();
        mockUser.setUsername("user13");
        zamianaMock1.setUser(mockUser);
        zamianaMock1.setCzasZamiany(LocalDateTime.now());

        Zamiana zamianaMock2 = new Zamiana();
        zamianaMock2.setWartoscWejsciowa(Math.PI);
        zamianaMock2.setJednostkaWejsciowa(Jednostka.RADIANY);
        zamianaMock2.setWartoscWynikowa(180.0);
        zamianaMock2.setJednostkaWynikowa(Jednostka.STOPNIE);
        DBUser mockUser2 = new DBUser();
        mockUser2.setUsername("user13");
        zamianaMock2.setUser(mockUser2);
        zamianaMock2.setCzasZamiany(LocalDateTime.now());

        List<Zamiana> zamianyLista = new ArrayList<>();
        zamianyLista.add(zamianaMock1);
        zamianyLista.add(zamianaMock2);


        when(zamianaRepository.findAllByUser_Username("user13")).thenReturn(zamianyLista);


        List<ZamianaTransDataOdpowiedz> transData = zamianaService.getWszystkieZamiany("user13");


        assertEquals(2, transData.size());
        assertEquals(Math.PI / 2, transData.get(0).getWartoscWynikowa());
        assertEquals("user13", transData.get(0).getUsername());
        assertEquals(180.0, transData.get(1).getWartoscWynikowa());
        assertEquals("user13", transData.get(1).getUsername());
        verify(zamianaRepository, times(1)).findAllByUser_Username("user13");
    }
}