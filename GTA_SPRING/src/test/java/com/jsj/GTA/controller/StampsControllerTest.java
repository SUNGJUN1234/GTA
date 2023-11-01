package com.jsj.GTA.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsj.GTA.domain.stamps.Stamps;
import com.jsj.GTA.domain.stamps.StampsRepository;
import com.jsj.GTA.domain.stamps.StampsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StampsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StampsRepository stampsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        stampsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스탬프저장() throws Exception {
        //given
        // id, gta id, user id, date, expire date
        String touristAttractionsId = "1";
        Long usersId = 1L;
        String name = "stamp name";
        String issueDate = new Date(System.currentTimeMillis()).toString();
        String expirationDate = new Date(System.currentTimeMillis()+365*24*60*60*1000).toString();
        StampsSaveRequestDto requestDto = StampsSaveRequestDto.builder()
                .touristAttractionsId(touristAttractionsId)
                .usersId(usersId)
                .name(name)
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .build();
        String url = "http://localhost:"+port+"/api/v1/stamps";
        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        //then
        List<Stamps> all = stampsRepository.findAll();
        assertThat(all.get(0).getTouristAttractionsId().equals(touristAttractionsId));
        assertThat(all.get(0).getUsersId().equals(usersId));
        assertThat(all.get(0).getName().equals(name));
        assertThat(all.get(0).getIssueDate().equals(issueDate));
//        assertThat(all.get(0).getExpirationDate().equals(expirationDate));
    }
}
