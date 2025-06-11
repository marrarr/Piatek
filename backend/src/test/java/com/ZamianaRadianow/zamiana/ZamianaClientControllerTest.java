package com.ZamianaRadianow.zamiana;

import com.ZamianaRadianow.zamiana.model.Jednostka;
import com.ZamianaRadianow.zamiana.transdata.ZamianaTransData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ZamianaClientControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "user1")
    void konwertuj_ShouldReturnConvertedValue() throws Exception {
        ZamianaTransData request = new ZamianaTransData();
        request.setWartoscWejsciowa(90.0);
        request.setJednostkaWejsciowa(Jednostka.STOPNIE);
        request.setJednostkaWynikowa(Jednostka.RADIANY);

        mockMvc.perform(post("/api/client/zamiany/konwertuj")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(Math.PI/2)));
    }

    @Test
    @WithMockUser(username = "testUser")
    void getMojeWymiany_ShouldReturnOnlyUserRecords() throws Exception {
        mockMvc.perform(get("/api/client/zamiany/moje"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    void konwertuj_ShouldBeUnauthorizedWithoutAuth() throws Exception {
        mockMvc.perform(post("/api/client/zamiany/konwertuj"))
                .andExpect(status().isUnauthorized());
    }
}