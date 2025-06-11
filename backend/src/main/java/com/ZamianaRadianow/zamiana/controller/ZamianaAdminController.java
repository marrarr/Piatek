package com.ZamianaRadianow.zamiana.controller;

import com.ZamianaRadianow.zamiana.ZamianaService;
import com.ZamianaRadianow.zamiana.transdata.StatystykiIloscPerUserAndJednostka;
import com.ZamianaRadianow.zamiana.transdata.StatystykiIloscPerUserTransData;
import com.ZamianaRadianow.zamiana.transdata.ZamianaTransDataOdpowiedz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/zamiany")
public class ZamianaAdminController {
    private final ZamianaService zamianaService;

    public ZamianaAdminController(ZamianaService zamianaService) {
        this.zamianaService = zamianaService;
    }

    @GetMapping("/wszystkie")
    public List<ZamianaTransDataOdpowiedz> getWszystkieWymiany() {
        return zamianaService.getWszystkieZamiany();
    }

    @GetMapping("/uzytkownik/{username}")
    public List<ZamianaTransDataOdpowiedz> getWymianyUzytkownika(@PathVariable String username) {
        return zamianaService.getWszystkieZamiany(username);
    }

    @DeleteMapping("/usuwanie/{id}")
    public ResponseEntity<Void> usunRekord(@PathVariable Long id) {
        zamianaService.usunRekord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statIloscPerUser")
    public List<StatystykiIloscPerUserTransData> getStatystykiIloscPerUser() {
        return zamianaService.getStatystykiIloscPerUser();
    }

    @GetMapping("/statIloscPerUserAndJednostka")
    public List<StatystykiIloscPerUserAndJednostka> getStatystykiIloscPerUserAndJednostka() {
        return zamianaService.getStatystykiIloscPerUserAndJednostka();
    }
}
