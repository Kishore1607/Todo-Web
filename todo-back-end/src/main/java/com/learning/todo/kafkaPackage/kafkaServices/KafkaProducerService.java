package com.learning.todo.kafkaPackage.kafkaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String value) {
        kafkaTemplate.send(topic, value);
        System.out.print("----------------------------- send");
    }
}




