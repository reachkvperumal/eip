package com.kv.dsl.eip.config;

import com.kv.dsl.eip.dto.IncidentDO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executors;

import static com.kv.dsl.eip.channel.ChannelName.*;
import static org.springframework.http.HttpMethod.POST;

@Log4j2
@Configuration
public class IntegrationConfig {

    @Value("${controller.broadcast}")
    private String endpoint;
    @Bean
    public IntegrationFlow singleChannelNotification() {
        return IntegrationFlow.from(Http.inboundGateway("/inbound")
                        .requestMapping(r -> r.methods(POST))
                        .requestPayloadType(String.class))
                .handle(Http.outboundGateway("http://localhost:8081/api/v1/outbound")
                        .httpMethod(POST)
                        .expectedResponseType(String.class))
                .get();
    }

    @Bean
    public IntegrationFlow broadcast() {
        return IntegrationFlow.from(Http.inboundGateway(endpoint)
                        .requestMapping(r -> r.methods(POST))
                        .requestPayloadType(IncidentDO.class))
                .publishSubscribeChannel(Executors.newCachedThreadPool(),
                        c -> c
                                .subscribe(p -> p.channel(PORTAL))
                                .subscribe(p -> p.channel(ALERTS)))
                .handle(((payload, headers) -> "SUCCESS"))
                .get();
    }

}