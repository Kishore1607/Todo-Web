package com.learning.todo.kafkaPackage.kafkaServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = {"user"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUser(String value) {
        System.out.println("---------------------------------------------");
        System.out.println("Received Kafka user message "+value);
        logger.info("Received Kafka user message - Value: {}", value);
        System.out.println("---------------------------------------------");
    }

    @KafkaListener(topics = {"task"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTask(String value) {
        System.out.println("---------------------------------------------");
        System.out.println("Received Kafka task message "+value);
        logger.info("Received Kafka task message - Value: {}", value);
        System.out.println("---------------------------------------------");
    }

}
