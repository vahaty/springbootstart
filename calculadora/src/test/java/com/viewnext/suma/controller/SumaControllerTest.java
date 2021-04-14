package com.viewnext.suma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.service.SumaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SumaController.class)
public class SumaControllerTest{

    @MockBean
    private SumaService multiplicationService;

    @Autowired
    private MockMvc mvc;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Suma> json;

    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getRandomSumasTest() throws Exception{
        // given
        given(multiplicationService.createRandomPlusOperation()).willReturn(new Suma(70, 20));

        // when
        final MockHttpServletResponse response = mvc.perform(get("/sumas/random").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(json.write(new Suma(70, 20)).getJson());
    }

}
