package com.bilalkristiania.AMQPPayment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiveOrderEvent {

    @RabbitListener(queues = "${amqp.queue.order}") // Replace with the actual queue name
    public void receiveOrderEvent(OrderEvent orderEvent) {
        log.info("Received Order Event in Payment Service: {}", orderEvent);
        // Add your processing logic here if needed
    }
}
