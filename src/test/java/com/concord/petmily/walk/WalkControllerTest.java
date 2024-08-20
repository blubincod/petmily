package com.concord.petmily.walk;

import com.concord.petmily.domain.walk.controller.WalkController;
import com.concord.petmily.domain.walk.service.WalkServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(WalkController.class)
public class WalkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalkServiceImpl walkServiceImpl;

    @Test
    @WithMockUser(username = "user1")
    void testStartWalk() throws Exception {

        // Given
        LocalDateTime startTime = LocalDateTime.of(2024, 8, 16, 5, 0);

        // When
        // Then
    }
}
