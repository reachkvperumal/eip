package com.kv.dsl.eip.config;

import com.kv.dsl.eip.dto.ResponseDO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

import static com.kv.dsl.eip.channel.ChannelName.PORTAL;

@Log4j2
@Configuration
public class GatewayPortalOutbound {

    @Value("${endpoint.portal}")
    private String endpoint;


    @Bean(name = PORTAL)
    public MessageChannel notifyPortal() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow portal() {
        log.info("Endpoint: {}", endpoint);
        return IntegrationFlow
                .from(PORTAL)
              //  .log(LoggingHandler.Level.ERROR)
                .handle(Http
                        .outboundGateway(endpoint)
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(ResponseDO.class))
                .get();
    }
}