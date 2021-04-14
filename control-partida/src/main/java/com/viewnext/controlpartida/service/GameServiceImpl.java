package com.viewnext.controlpartida.service;

import com.viewnext.controlpartida.client.SumaResultAttemptClient;
import com.viewnext.controlpartida.client.dto.SumaResultAttempt;
import com.viewnext.controlpartida.domain.Badge;
import com.viewnext.controlpartida.domain.BadgeCard;
import com.viewnext.controlpartida.domain.GameStats;
import com.viewnext.controlpartida.domain.ScoreCard;
import com.viewnext.controlpartida.repository.BadgeCardRepository;
import com.viewnext.controlpartida.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class GameServiceImpl implements GameService{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GameServiceImpl.class);

    public static final int LUCKY_NUMBER = 42;

    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;
    private final SumaResultAttemptClient attemptClient;

    GameServiceImpl(final ScoreCardRepository scoreCardRepository, final BadgeCardRepository badgeCardRepository,
            final SumaResultAttemptClient attemptClient){
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
        this.attemptClient = attemptClient;
    }

    @Override
    public GameStats newAttemptForUser(final Long userId, final Long attemptId, final boolean correct){
        // For the first version we'll give points only if it's correct
        if(correct){
            final ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);
            final List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
            return new GameStats(userId, scoreCard.getScore(),
                    badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
        }
        return GameStats.emptyStats(userId);
    }

    /**
     * Checks the total score and the different score cards obtained
     * to give new badges in case their conditions are met.
     */
    private List<BadgeCard> processForBadges(final Long userId, final Long attemptId){
        final List<BadgeCard> badgeCards = new ArrayList<>();

        final int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("New score for user {} is {}", userId, totalScore);

        final List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        final List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

        // Badges depending on score
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
                .ifPresent(badgeCards::add);

        // First won badge
        if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)){
            final BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
            badgeCards.add(firstWonBadge);
        }

        // Lucky number badge
        final SumaResultAttempt attempt = attemptClient.retrieveSumaResultAttemptbyId(attemptId);
        if(!containsBadge(badgeCardList, Badge.LUCKY_NUMBER) && (LUCKY_NUMBER == attempt
                .getSumaFactorA() || LUCKY_NUMBER == attempt.getSumaFactorB())){
            final BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
            badgeCards.add(luckyNumberBadge);
        }

        return badgeCards;
    }

    @Override
    public GameStats retrieveStatsForUser(final Long userId){
        final int score = scoreCardRepository.getTotalScoreForUser(userId);
        final List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
    }

    /**
     * Convenience method to check the current score against
     * the different thresholds to gain badges.
     * It also assigns badge to user if the conditions are met.
     */
    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge,
            final int score, final int scoreThreshold, final Long userId){
        if(score >= scoreThreshold && !containsBadge(badgeCards, badge)){
            return Optional.of(giveBadgeToUser(badge, userId));
        }
        return Optional.empty();
    }

    /**
     * Checks if the passed list of badges includes the one being checked
     */
    private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge){
        return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
    }

    /**
     * Assigns a new badge to the given user
     */
    private BadgeCard giveBadgeToUser(final Badge badge, final Long userId){
        final BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("User with id {} won a new badge: {}", userId, badge);
        return badgeCard;
    }

}
