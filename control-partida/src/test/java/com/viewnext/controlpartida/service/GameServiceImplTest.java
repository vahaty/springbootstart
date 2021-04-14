package com.viewnext.controlpartida.service;

import com.viewnext.controlpartida.client.SumaResultAttemptClient;
import com.viewnext.controlpartida.client.dto.SumaResultAttempt;
import com.viewnext.controlpartida.domain.Badge;
import com.viewnext.controlpartida.domain.BadgeCard;
import com.viewnext.controlpartida.domain.GameStats;
import com.viewnext.controlpartida.domain.ScoreCard;
import com.viewnext.controlpartida.repository.BadgeCardRepository;
import com.viewnext.controlpartida.repository.ScoreCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class GameServiceImplTest{

    private GameServiceImpl gameService;

    @Mock
    private ScoreCardRepository scoreCardRepository;

    @Mock
    private BadgeCardRepository badgeCardRepository;

    @Mock
    private SumaResultAttemptClient multiplicationClient;

    @BeforeEach
    public void setUp(){
        // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository, multiplicationClient);

        // Common given - attempt does not contain a lucky number by default
        final SumaResultAttempt attempt = new SumaResultAttempt("john_doe", 20, 70, 1400, true);
        given(multiplicationClient.retrieveSumaResultAttemptbyId(anyLong())).willReturn(attempt);
    }

    @Test
    public void processFirstCorrectAttemptTest(){
        // given
        final Long userId = 1L;
        final Long attemptId = 8L;
        final int totalScore = 10;
        final ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());

        // when
        final GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

        // assert - should score one card and win the badge FIRST_WON
        assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
        assertThat(iteration.getBadges()).containsOnly(Badge.FIRST_WON);
    }

    @Test
    public void processCorrectAttemptForScoreBadgeTest(){
        // given
        final Long userId = 1L;
        final Long attemptId = 29L;
        final int totalScore = 100;
        final BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(10, userId));
        // the first won badge is already there
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(firstWonBadge));

        // when
        final GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

        // assert - should score one card and win the badge BRONZE
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
    }

    @Test
    public void processCorrectAttemptForLuckyNumberBadgeTest(){
        // given
        final Long userId = 1L;
        final Long attemptId = 29L;
        final int totalScore = 10;
        final BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(1, userId));
        // the first won badge is already there
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(firstWonBadge));
        // the attempt includes the lucky number
        final SumaResultAttempt attempt = new SumaResultAttempt("john_doe", 42, 10, 420, true);
        given(multiplicationClient.retrieveSumaResultAttemptbyId(attemptId)).willReturn(attempt);

        // when
        final GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

        // assert - should score one card and win the badge LUCKY NUMBER
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).containsOnly(Badge.LUCKY_NUMBER);
    }

    @Test
    public void processWrongAttemptTest(){
        // given
        final Long userId = 1L;
        final Long attemptId = 8L;
        final int totalScore = 10;
        final ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        // this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());

        // when
        final GameStats iteration = gameService.newAttemptForUser(userId, attemptId, false);

        // assert - shouldn't score anything
        assertThat(iteration.getScore()).isEqualTo(0);
        assertThat(iteration.getBadges()).isEmpty();
    }

    @Test
    public void retrieveStatsForUserTest(){
        // given
        final Long userId = 1L;
        final int totalScore = 1000;
        final BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(badgeCard));

        // when
        final GameStats stats = gameService.retrieveStatsForUser(userId);

        // assert - should score one card and win the badge FIRST_WON
        assertThat(stats.getScore()).isEqualTo(totalScore);
        assertThat(stats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
    }

    private List<ScoreCard> createNScoreCards(final int n, final Long userId){
        return IntStream.range(0, n).mapToObj(i -> new ScoreCard(userId, (long) i)).collect(Collectors.toList());
    }
}