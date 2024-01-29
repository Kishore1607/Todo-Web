package com.learning.todo.kafkaPackage.kafkaServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTaskConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTaskConsumerService.class);
    @KafkaListener(topics = {"task"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTask(String value) {
        logger.info("Task message - Value: {}", value);
    }

}
