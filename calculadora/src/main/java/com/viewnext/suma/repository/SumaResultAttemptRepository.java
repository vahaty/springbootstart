package com.viewnext.suma.repository;

import org.springframework.data.repository.CrudRepository;

import com.viewnext.suma.domain.SumaResultAttempt;

import java.util.List;

/**
 * This interface allow us to store and retrieve attempts
 */
public interface SumaResultAttemptRepository
        extends CrudRepository<SumaResultAttempt, Long> {

    /**
     * @return the latest 5 attempts for a given user, identified by their alias.
     */
    List<SumaResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
