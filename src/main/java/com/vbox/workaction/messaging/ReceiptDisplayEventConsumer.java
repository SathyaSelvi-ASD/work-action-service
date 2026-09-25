package com.vbox.workaction.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Receives receipt-display notifications emitted by the Disclosure service. */
@Service
public class ReceiptDisplayEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(ReceiptDisplayEventConsumer.class);

    @KafkaListener(
            topics = "${app.kafka.topics.receipt-display}",
            groupId = "${app.kafka.consumer.receipt-display-group}",
            autoStartup = "${app.kafka.consumer.receipt-display-auto-startup:true}")
    public void consume(ReceiptDisplayEvent event) {
        log.info("Received receipt display event eventId={} receiptId={} workActionId={}",
                event.eventId(), event.receiptId(), event.workActionId());
    }
}
