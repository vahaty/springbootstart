package com.viewnext.suma.service;

import java.util.List;

import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.domain.SumaResultAttempt;

public interface SumaService {

    /**
     * Creates a Multiplication object with two randomly-generated factors
     * between 11 and 99.
     *
     * @return a Multiplication object with random factors
     */
    Suma createRandomPlusOperation();

    /**
     * @return a {@link SumaResultAttempt}, which contains information about the attempt and
     * indicates if it's correct or not.
     */
    SumaResultAttempt checkAttempt(final SumaResultAttempt resultAttempt);

    /**
     * Gets the statistics for a given user.
     *
     * @param userAlias the user's alias
     * @return a list of {@link SumaResultAttempt} objects, being the past attempts of the user.
     */
    List<SumaResultAttempt> getStatsForUser(final String userAlias);

    /**
     * Gets an attempt by its id
     *
     * @param resultId the identifier of the attempt
     * @return the {@link SumaResultAttempt} object matching the id, otherwise null.
     */
    SumaResultAttempt getResultById(final Long resultId);

}
