package pl.filiphagno.spring6icecoldservice.listeners;

import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.filiphagno.spring6icecoldservice.config.KafkaConfig;
import pl.filiphagno.spring6icecoldservice.services.DrinkRequestProcessor;

@Slf4j
@RequiredArgsConstructor
@Component
public class DrinkRequestListener {

    private final DrinkRequestProcessor drinkRequestProcessor;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(groupId = "IceColdListener", topics = KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC)
    public void listenDrinkRequest(DrinkRequestEvent event) {
        log.debug("I am listening - drink request");

        drinkRequestProcessor.processDrinkRequest(event);

        kafkaTemplate.send(KafkaConfig.DRINK_PREPARED_TOPIC, DrinkPreparedEvent.builder()
                .beerOrderLine(event.getBeerOrderLineDTO())
                .build());

    }
}
