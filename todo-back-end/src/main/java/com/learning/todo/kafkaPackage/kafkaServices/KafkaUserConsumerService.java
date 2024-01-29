package com.learning.todo.kafkaPackage.kafkaServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaUserConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaUserConsumerService.class);
    @KafkaListener(topics = {"user"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUser(String value) {
        logger.info("User message - Value: {}", value);
    }
}
