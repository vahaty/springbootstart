package com.viewnext.suma.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Identifies the attempt from a {@link User} to solve a {@link Suma}.
 */
@SuppressWarnings("serial")
@Entity
public final class SumaResultAttempt implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + resultAttempt;
		result = prime * result + ((suma == null) ? 0 : suma.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		SumaResultAttempt other = (SumaResultAttempt) obj;
		if (correct != other.correct)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resultAttempt != other.resultAttempt)
			return false;
		if (suma == null) {
			if (other.suma != null)
				return false;
		} else if (!suma.equals(other.suma))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	private final User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "SUMA_ID")
	private final Suma suma;
	private final int resultAttempt;

	private final boolean correct;

	public SumaResultAttempt(User user, Suma suma, int resultAttempt, boolean correct) {
		super();
		this.user = user;
		this.suma = suma;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public Suma getSuma() {
		return suma;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}

	public boolean isCorrect() {
		return correct;
	}

	// Empty constructor for JSON/JPA
	SumaResultAttempt() {
		user = null;
		suma = null;
		resultAttempt = -1;
		correct = false;
	}

}
