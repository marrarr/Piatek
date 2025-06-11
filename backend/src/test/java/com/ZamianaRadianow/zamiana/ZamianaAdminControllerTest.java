package com.ZamianaRadianow.zamiana;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ZamianaAdminControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getWszystkieWymiany_ShouldReturnAllRecords() throws Exception {
        mockMvc.perform(get("/api/admin/zamiany/wszystkie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getWymianyUzytkownika_ShouldFilterByUsername() throws Exception {
        String username = "specificUser";
        mockMvc.perform(get("/api/admin/zamiany/uzytkownik/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(username));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void usunRekord_ShouldDeleteRecord() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/admin/zamiany/usuwanie/{id}", id).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER") // Nie-ADMIN nie powinien mieć dostępu
    void usunRekord_ShouldForbidNonAdmin() throws Exception {
        mockMvc.perform(delete("/api/admin/zamiany/usuwanie/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}