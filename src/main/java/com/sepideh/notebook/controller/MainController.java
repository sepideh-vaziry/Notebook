package com.sepideh.notebook.controller;

import com.sepideh.notebook.config.RabbitmqConfig;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {

    private final RabbitTemplate rabbitTemplate;

    //******************************************************************************************************************
    public MainController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/test-rabbitmq", method = RequestMethod.POST)
    public ResponseEntity<GenericRestResponse<Boolean>> testRabbitMQ() {

        rabbitTemplate.convertAndSend(
            RabbitmqConfig.TOPIC_EXCHANGE_NAME,
            String.format(RabbitmqConfig.ROUTING_KEY_TEST, "first"),
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

}
