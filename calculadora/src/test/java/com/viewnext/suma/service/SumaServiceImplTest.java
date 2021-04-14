package com.viewnext.suma.service;

import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.domain.SumaResultAttempt;
import com.viewnext.suma.domain.User;
import com.viewnext.suma.event.EventDispatcher;
import com.viewnext.suma.event.SumaSolvedEvent;
import com.viewnext.suma.repository.SumaResultAttemptRepository;
import com.viewnext.suma.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class SumaServiceImplTest{

    private SumaServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private SumaResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @BeforeEach
    public void setUp(){
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new SumaServiceImpl(randomGeneratorService, attemptRepository, userRepository,
                eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest(){
        // given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when
        final Suma multiplication = multiplicationServiceImpl.createRandomPlusOperation();

        // then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest(){
        // given
        final Suma suma = new Suma(50, 60);
        final User user = new User("john_doe");
        final SumaResultAttempt attempt = new SumaResultAttempt(user, suma, 110, false);
        final SumaResultAttempt verifiedAttempt = new SumaResultAttempt(user, suma, 110, true);
        final SumaSolvedEvent event = new SumaSolvedEvent(attempt.getId(), attempt.getUser().getId(), true);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        // Note: the service will set correct to true
        given(attemptRepository.save(verifiedAttempt)).willReturn(verifiedAttempt);

        // when
        final SumaResultAttempt resultAttempt = multiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(resultAttempt.isCorrect()).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void checkWrongAttemptTest(){
        // given
        final Suma multiplication = new Suma(50, 60);
        final User user = new User("john_doe");
        final SumaResultAttempt attempt = new SumaResultAttempt(user, multiplication, 100, false);
        final SumaResultAttempt storedAttempt = new SumaResultAttempt(user, multiplication, 100, false);
        final SumaSolvedEvent event = new SumaSolvedEvent(attempt.getId(), attempt.getUser().getId(), false);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.save(attempt)).willReturn(storedAttempt);

        // when
        final SumaResultAttempt resultAttempt = multiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(resultAttempt.isCorrect()).isFalse();
        verify(attemptRepository).save(attempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void retrieveStatsTest(){
        // given
        final Suma multiplication = new Suma(50, 60);
        final User user = new User("john_doe");
        final SumaResultAttempt attempt1 = new SumaResultAttempt(user, multiplication, 100, false);
        final SumaResultAttempt attempt2 = new SumaResultAttempt(user, multiplication, 233, false);
        final List<SumaResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe")).willReturn(latestAttempts);

        // when
        final List<SumaResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("john_doe");

        // then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }
}