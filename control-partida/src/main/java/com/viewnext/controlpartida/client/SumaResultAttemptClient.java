package com.viewnext.controlpartida.client;

import com.viewnext.controlpartida.client.dto.SumaResultAttempt;

/**
 * This interface allows us to connect to the Calculadora microservice.
 * Note that it's agnostic to the way of communication.
 */
public interface SumaResultAttemptClient {

    SumaResultAttempt retrieveSumaResultAttemptbyId(final Long sumaId);

}
