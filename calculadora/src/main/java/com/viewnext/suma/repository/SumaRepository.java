package com.viewnext.suma.repository;

import org.springframework.data.repository.CrudRepository;

import com.viewnext.suma.domain.Suma;

/**
 * This interface allows us to save and retrieve Multiplications
 */
public interface SumaRepository extends CrudRepository<Suma, Long> {
}
