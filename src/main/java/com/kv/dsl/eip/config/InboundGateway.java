package com.kv.dsl.eip.config;

import com.kv.dsl.eip.dto.IncidentDO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

import static com.kv.dsl.eip.channel.ChannelName.INCIDENT_PAYLOAD;
import static com.kv.dsl.eip.channel.ChannelName.PORTAL;

@Log4j2
@Configuration
public class InboundGateway {

    @Value("${controller.path}")
    private String path;
    @Bean(name = INCIDENT_PAYLOAD)
    public MessageChannel requestChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inbound() {
        log.info("Inbound initialized...");
        return IntegrationFlow
                .from(
                        Http
                                .inboundGateway(path)
                                .convertExceptions(true)
                                .requestMapping(m -> {
                                    m.methods(HttpMethod.POST);
                                })
                                .requestPayloadType(IncidentDO.class))
                .log(LoggingHandler.Level.ERROR)
                .channel(INCIDENT_PAYLOAD)
                .get();
    }

    @ServiceActivator(inputChannel = INCIDENT_PAYLOAD, outputChannel = PORTAL)
    public Message<IncidentDO> createRequest(Message<IncidentDO> message) {
        log.info("Priority: {}, Details: {}", message.getPayload().getPriority(),message.getPayload().getDetails());
        return MessageBuilder
                .withPayload(message.getPayload())
                .setHeader("x-request-id", UUID.randomUUID())
                .build();
    }
}
