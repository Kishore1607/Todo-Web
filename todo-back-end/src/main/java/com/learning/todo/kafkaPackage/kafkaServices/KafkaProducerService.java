package com.learning.todo.kafkaPackage.kafkaServices;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<Long, String> userTemplate;
    private final KafkaTemplate<Long, String> taskTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<Long, String> userTemplate, KafkaTemplate<Long, String> taskTemplate) {
        this.userTemplate = userTemplate;
        this.taskTemplate = taskTemplate;
    }

    public void sendTaskMessage(String topic, String value) {
        taskTemplate.send(new ProducerRecord<>(topic, value));
    }

    public void sendUserMessage(String topic, String value) {
        userTemplate.send(new ProducerRecord<>(topic, value));
    }
}
