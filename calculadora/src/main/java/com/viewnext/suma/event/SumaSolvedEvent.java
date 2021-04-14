package com.viewnext.suma.event;

import java.io.Serializable;

/**
 * Event that models the fact that a {@link com.viewnext.suma.domain.Suma}
 * has been solved in the system. Provides some context information about the plus operation.
 */

@SuppressWarnings("serial")
public class SumaSolvedEvent implements Serializable {

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + ((sumaResultAttemptId == null) ? 0 : sumaResultAttemptId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SumaSolvedEvent other = (SumaSolvedEvent) obj;
		if (correct != other.correct)
			return false;
		if (sumaResultAttemptId == null) {
			if (other.sumaResultAttemptId != null)
				return false;
		} else if (!sumaResultAttemptId.equals(other.sumaResultAttemptId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	private final Long sumaResultAttemptId;
    private final Long userId;
    private final boolean correct;
	public SumaSolvedEvent(Long sumaResultAttemptId, Long userId, boolean correct) {
		super();
		this.sumaResultAttemptId = sumaResultAttemptId;
		this.userId = userId;
		this.correct = correct;
	}
	public Long getSumaResultAttemptId() {
		return sumaResultAttemptId;
	}
	public Long getUserId() {
		return userId;
	}
	public boolean isCorrect() {
		return correct;
	}

}
