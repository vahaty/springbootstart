package com.viewnext.suma.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rabbit.suma")
public class SumaQueueConfiguration{

    private String exchange;
    private String solvedKey;

    public String getSolvedKey(){
        return solvedKey;
    }

    public void setSolvedKey(final String solvedKey){
        this.solvedKey = solvedKey;
    }

    public String getExchange(){
        return exchange;
    }

    public void setExchange(final String exchange){
        this.exchange = exchange;
    }
}
