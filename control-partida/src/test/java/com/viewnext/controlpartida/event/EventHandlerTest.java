package com.viewnext.controlpartida.event;

import com.viewnext.controlpartida.domain.GameStats;
import com.viewnext.controlpartida.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class EventHandlerTest{

    private EventHandler eventHandler;

    @Mock
    private GameService gameService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        eventHandler = new EventHandler(gameService);
    }

    @Test
    public void multiplicationAttemptReceivedTest(){
        // given
        final Long userId = 1L;
        final Long attemptId = 8L;
        final boolean correct = true;
        final GameStats gameStatsExpected = new GameStats();
        final SumaSolvedEvent event = new SumaSolvedEvent(attemptId, userId, correct);
        given(gameService.newAttemptForUser(userId, attemptId, correct)).willReturn(gameStatsExpected);

        // when
        eventHandler.handleMultiplicationSolved(event);

        // then
        verify(gameService).newAttemptForUser(userId, attemptId, correct);
    }

}