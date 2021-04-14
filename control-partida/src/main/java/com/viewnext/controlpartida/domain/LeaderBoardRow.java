package com.viewnext.controlpartida.domain;

/**
 * Represents a line in our Leaderboard: it links a user to a total score.
 */
public final class LeaderBoardRow {

    private final Long userId;
    private final Long totalScore;

    public LeaderBoardRow(Long userId, Long totalScore) {
		super();
		this.userId = userId;
		this.totalScore = totalScore;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	// Empty constructor for JSON / JPA
    public LeaderBoardRow() {
        this(0L, 0L);
    }
}
