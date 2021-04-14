package com.viewnext.gateway.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;

public class RibbonConfiguration{

    /**
     * Valida que los servicios estén levantados y no tengan errores (bd, rabbit, etc..)
     * Eliminar este método para evitar errores de validación contra los microservicios cuando no esté levantado rabbitMQ
     * @param config
     * @return
     */
    @Bean
    public IPing ribbonPing(final IClientConfig config){

        return new PingUrl(false, "/health");
    }

    @Bean
    public IRule ribbonRule(final IClientConfig config){
        return new AvailabilityFilteringRule();
    }

}
