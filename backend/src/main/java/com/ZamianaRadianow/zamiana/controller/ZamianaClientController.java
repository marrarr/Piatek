package com.ZamianaRadianow.zamiana.controller;

import com.ZamianaRadianow.zamiana.ZamianaService;
import com.ZamianaRadianow.zamiana.transdata.ZamianaTransData;
import com.ZamianaRadianow.zamiana.transdata.ZamianaTransDataOdpowiedz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/zamiany")
public class ZamianaClientController {
    @Autowired
    private ZamianaService zamianaService;

    @PostMapping("/konwertuj")
    public double konwertuj(
            @RequestBody ZamianaTransData zamianaTransData
    ) {
        System.out.println(zamianaTransData.getWartoscWejsciowa());
        System.out.println(zamianaTransData.getJednostkaWejsciowa());
        System.out.println(zamianaTransData.getJednostkaWynikowa());
        return zamianaService.konwertuj(
                zamianaTransData.getWartoscWejsciowa(),
                zamianaTransData.getJednostkaWejsciowa(),
                zamianaTransData.getJednostkaWynikowa()
        );
    }

    @GetMapping("/moje")
    public List<ZamianaTransDataOdpowiedz> getMojeWymiany() {  // endpoint dla klienta
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return zamianaService.getWszystkieZamiany(username);
    }
}