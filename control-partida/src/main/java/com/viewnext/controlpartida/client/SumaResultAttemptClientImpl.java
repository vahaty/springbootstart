package com.viewnext.controlpartida.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.viewnext.controlpartida.client.dto.SumaResultAttempt;

/**
 * This implementation of SumaResultAttemptClient interface connects to
 * the Calculadora microservice via REST.
 */
@Component
class SumaResultAttemptClientImpl implements SumaResultAttemptClient {

    private final RestTemplate restTemplate;
    private final String multiplicationHost;

    @Autowired
    public SumaResultAttemptClientImpl(final RestTemplate restTemplate,
                                                 @Value("${calculadoraHost}") final String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationHost = multiplicationHost;
    }

    @HystrixCommand(fallbackMethod = "defaultResult")
    @Override
    public SumaResultAttempt retrieveSumaResultAttemptbyId(final Long sumaResultAttemptId) {
        return restTemplate.getForObject(
                multiplicationHost + "/resultados-suma/" + sumaResultAttemptId,
                SumaResultAttempt.class);
    }

    @SuppressWarnings("unused")
	private SumaResultAttempt defaultResult(final Long multiplicationResultAttemptId) {
        return new SumaResultAttempt("fakeAlias",
                10, 10, 100, true);
    }
}
