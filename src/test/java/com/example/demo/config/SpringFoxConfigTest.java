package com.example.demo.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringFoxConfigTest {

    @Autowired
    private SpringFoxConfig springFoxConfig;

    @Test
    @DisplayName("Should return a Docket object")
    public void testApiShouldReturnDocketObject() {
        Docket docket = springFoxConfig.api();
        assertNotNull(docket);
    }
}