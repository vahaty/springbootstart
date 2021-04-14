package com.viewnext.controlpartida.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents the Score linked to an attempt in the game,
 * with an associated user and the timestamp in which the score
 * is registered.
 */
@Entity
public final class ScoreCard {

    // The default score assigned to this card, if not specified.
    public static final int DEFAULT_SCORE = 10;

    @Id
    @GeneratedValue
    @Column(name = "CARD_ID")
    private final Long cardId;

    @Column(name = "USER_ID")
    private final Long userId;

    @Column(name = "ATTEMPT_ID")
    private final Long attemptId;

    @Column(name = "SCORE_TS")
    private final long scoreTimestamp;

    @Column(name = "SCORE")
    private final int score;

    public ScoreCard(Long cardId, Long userId, Long attemptId, long scoreTimestamp, int score) {
		super();
		this.cardId = cardId;
		this.userId = userId;
		this.attemptId = attemptId;
		this.scoreTimestamp = scoreTimestamp;
		this.score = score;
	}

	public Long getCardId() {
		return cardId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getAttemptId() {
		return attemptId;
	}

	public long getScoreTimestamp() {
		return scoreTimestamp;
	}

	public int getScore() {
		return score;
	}

	// Empty constructor for JSON / JPA
    public ScoreCard() {
        this(null, null, null, 0, 0);
    }

    public ScoreCard(final Long userId, final Long attemptId) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }

}
