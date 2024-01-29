package com.learning.todo.kafkaPackage;
import com.learning.todo.kafkaPackage.kafkaServices.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/send-message")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/task")
    public String sendTaskMessage(@RequestParam String topic, @RequestParam String value) {
        kafkaProducerService.sendTaskMessage(topic, value);
        return "Message sent to Kafka topic: " + topic;
    }

    @GetMapping("/user")
    public String sendUserMessage(@RequestParam String topic, @RequestParam String value) {
        kafkaProducerService.sendUserMessage(topic, value);
        return "Message sent to Kafka topic: " + topic;
    }
}
