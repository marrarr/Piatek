package com.ZamianaRadianow.log;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LogControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void showLog_ShouldReturnLogsForAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/show_log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "USER")
    void showLog_ShouldForbidNonAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/show_log"))
                .andExpect(status().isForbidden());
    }
}