package com.viewnext.controlpartida.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.viewnext.controlpartida.client.SumaResultAttemptDeserializer;

/**
 * Identifies the attempt from a user to solve a plus operation.
 */
@JsonDeserialize(using = SumaResultAttemptDeserializer.class)
public final class SumaResultAttempt {

    private final String userAlias;
	

	private final int sumaFactorA;
    private final int sumaFactorB;
    private final int resultAttempt;

    private final boolean correct;

    public SumaResultAttempt(String userAlias, int sumaFactorA, int sumaFactorB,
			int resultAttempt, boolean correct) {
		super();
		this.userAlias = userAlias;
		this.sumaFactorA = sumaFactorA;
		this.sumaFactorB = sumaFactorB;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}
    
    // Empty constructor for JSON/JPA
    SumaResultAttempt() {
        userAlias = null;
        sumaFactorA = -1;
        sumaFactorB = -1;
        resultAttempt = -1;
        correct = false;
    }
    
    public String getUserAlias() {
		return userAlias;
	}

	public int getSumaFactorA() {
		return sumaFactorA;
	}

	public int getSumaFactorB() {
		return sumaFactorB;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}

	public boolean isCorrect() {
		return correct;
	}

}
