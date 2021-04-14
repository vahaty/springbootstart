package com.viewnext.suma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.domain.SumaResultAttempt;
import com.viewnext.suma.domain.User;
import com.viewnext.suma.service.SumaService;
import org.assertj.core.util.Lists;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SumaResultAttemptController.class)
public class SumaResultAttemptControllerTest{

    @MockBean
    private SumaService multiplicationService;

    @Autowired
    private MockMvc mvc;

    // These objects will be magically initialized by the initFields method below.
    private JacksonTester<SumaResultAttempt> jsonResultAttempt;
    private JacksonTester<List<SumaResultAttempt>> jsonResultAttemptList;

    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception{
        genericParameterizedTest(true);
    }

    @Test
    public void postResultReturnNotCorrect() throws Exception{
        genericParameterizedTest(false);
    }

    void genericParameterizedTest(final boolean correct) throws Exception{
        // given (remember we're not testing here the service itself)
        final User user = new User("john");
        final Suma multiplication = new Suma(50, 70);
        final SumaResultAttempt attempt = new SumaResultAttempt(user, multiplication, 3500, correct);
        given(multiplicationService.checkAttempt(any(SumaResultAttempt.class))).willReturn(attempt);

        // when
        final MockHttpServletResponse response = mvc.perform(
                post("/resultados-suma").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResultAttempt.write(attempt).getJson())).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttempt
                .write(new SumaResultAttempt(attempt.getUser(), attempt.getSuma(), attempt.getResultAttempt(), correct))
                .getJson());
    }

    @Test
    public void getUserStats() throws Exception{
        // given
        final User user = new User("john_doe");
        final Suma multiplication = new Suma(50, 70);
        final SumaResultAttempt attempt = new SumaResultAttempt(user, multiplication, 3500, true);
        final List<SumaResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
        given(multiplicationService.getStatsForUser("john_doe")).willReturn(recentAttempts);

        // when
        final MockHttpServletResponse response = mvc.perform(get("/resultados-suma").param("alias", "john_doe"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());
    }

    @Test
    public void getResultByIdTest() throws Exception{
        // given
        final User user = new User("john_doe");
        final Suma multiplication = new Suma(50, 70);
        final SumaResultAttempt attempt = new SumaResultAttempt(user, multiplication, 3500, true);
        given(multiplicationService.getResultById(4L)).willReturn(attempt);

        // when
        final MockHttpServletResponse response = mvc.perform(get("/resultados-suma/4")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(attempt).getJson());
    }

}