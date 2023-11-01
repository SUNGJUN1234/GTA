package com.jsj.GTA.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 실제 URL 호출 시, 페이지의 내용이 제대로 호출되는 지에 대한 테스트
     */
    @Test
    public void 메인페이지_로딩() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")) // when
                .andExpect(MockMvcResultMatchers.status().isOk()) //then
                .andExpect(MockMvcResultMatchers.content().string("GUEST"));
    }
}
