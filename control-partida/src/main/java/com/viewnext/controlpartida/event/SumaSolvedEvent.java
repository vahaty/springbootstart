package com.viewnext.controlpartida.event;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event received when a plus operation has been solved in the system. Provides
 * some context information about the multiplication.
 */

@SuppressWarnings("serial")
class SumaSolvedEvent implements Serializable {

	private final Long sumaResultAttemptId;
	private final Long userId;
	private final boolean correct;

	@JsonCreator
	public SumaSolvedEvent(@JsonProperty("sumaResultAttemptId") Long sumaResultAttemptId,
			@JsonProperty("userId") Long userId, @JsonProperty("correct") boolean correct) {
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
