package com.viewnext.suma.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents a Plus operation (a + b).
 */

@SuppressWarnings("serial")
@Entity
public final class Suma implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + factorA;
		result = prime * result + factorB;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Suma other = (Suma) obj;
		if (factorA != other.factorA)
			return false;
		if (factorB != other.factorB)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Id
	@GeneratedValue
	@Column(name = "SUMA_ID")
	private Long id;

	// Both factors
	private final int factorA;
	private final int factorB;

	public Suma(Long id, int factorA, int factorB) {
		super();
		this.id = id;
		this.factorA = factorA;
		this.factorB = factorB;
	}

	public Suma(int factorA, int factorB) {
		super();
		this.factorA = factorA;
		this.factorB = factorB;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFactorA() {
		return factorA;
	}

	public int getFactorB() {
		return factorB;
	}

	// Empty constructor for JSON/JPA
	Suma() {
		this(0, 0);
	}
}
