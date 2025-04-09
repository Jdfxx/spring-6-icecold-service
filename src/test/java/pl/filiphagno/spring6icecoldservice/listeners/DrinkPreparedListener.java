package pl.filiphagno.spring6icecoldservice.listeners;

import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.filiphagno.spring6icecoldservice.config.KafkaConfig;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DrinkPreparedListener {

    public AtomicInteger iceColdMessageCounter = new AtomicInteger(0);

    @KafkaListener(topics = KafkaConfig.DRINK_PREPARED_TOPIC, groupId = "ice-cold-listener")
    public void listen(DrinkPreparedEvent event) {
        System.out.println("I'm listening...");

        iceColdMessageCounter.incrementAndGet();
    }
}
