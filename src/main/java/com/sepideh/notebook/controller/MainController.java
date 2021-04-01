package com.sepideh.notebook.controller;

import com.sepideh.notebook.config.KafkaConfig;
import com.sepideh.notebook.config.RabbitMQConfig;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {

    private final RabbitTemplate rabbitTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //******************************************************************************************************************
    public MainController(RabbitTemplate rabbitTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/test-rabbitmq", method = RequestMethod.POST)
    public ResponseEntity<GenericRestResponse<Boolean>> testRabbitMQ() {

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.TOPIC_EXCHANGE_NAME,
            String.format(RabbitMQConfig.ROUTING_KEY_TEST, "first"),
            "Hello from RabbitMQ!"
        );

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                true,
                "Success",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/test-kafka", method = RequestMethod.POST)
    public ResponseEntity<GenericRestResponse<Boolean>> testKafka() {
        System.out.println("test kafka");

        kafkaTemplate.send(KafkaConfig.TOPIC_NAME_TEST, "Hello from Kafka!");

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                true,
                "Success",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

}
