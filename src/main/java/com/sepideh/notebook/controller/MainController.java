package com.sepideh.notebook.controller;

import com.sepideh.notebook.config.RabbitMQConfig;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.mapper.UserMapper;
import com.sepideh.notebook.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {

    private final RabbitTemplate rabbitTemplate;
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final KafkaTemplate<String, SimpleUserDto> kafkaTemplateUser;

    private final UserMapper userMapper;

    //******************************************************************************************************************
    public MainController(
        RabbitTemplate rabbitTemplate,
//        KafkaTemplate<String, String> kafkaTemplate,
//        KafkaTemplate<String, SimpleUserDto> kafkaTemplateUser,
        UserMapper userMapper
    ) {
        this.rabbitTemplate = rabbitTemplate;
//        this.kafkaTemplate = kafkaTemplate;
//        this.kafkaTemplateUser = kafkaTemplateUser;
        this.userMapper = userMapper;
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/test-rabbitmq", method = RequestMethod.POST)
    public GenericRestResponse<Boolean> testRabbitMQ() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.TOPIC_EXCHANGE_USER,
            String.format(RabbitMQConfig.ROUTING_KEY_USER, user.getId()),
            user
        );

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.TOPIC_EXCHANGE_USER,
            String.format(RabbitMQConfig.ROUTING_KEY_USERNAME, user.getUsername()),
            user.getUsername()
        );

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.TOPIC_EXCHANGE_USER,
            String.format(RabbitMQConfig.ROUTING_KEY_ID, user.getId()),
            user.getId()
        );

        return new GenericRestResponse<>(
            true,
            "Success",
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
//    @RequestMapping(value = "/test-kafka", method = RequestMethod.POST)
//    public GenericRestResponse<Boolean> testKafka() {
//        System.out.println("test kafka");
//
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(
//            KafkaConfig.TOPIC_NAME_TEST,
//            "Hello from Kafka!"
//        );
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                System.out.println("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message due to : " + ex.getMessage());
//            }
//        });
//
//        return new GenericRestResponse<>(
////                true,
////                "Success",
////                HttpStatus.OK.value()
////            );
//    }
//
//    //******************************************************************************************************************
//    @RequestMapping(value = "/test-kafka-user", method = RequestMethod.POST)
//    public GenericRestResponse<Boolean> testKafkaUser() {
//        System.out.println("test kafka user");
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        ListenableFuture<SendResult<String, SimpleUserDto>> future = kafkaTemplateUser.send(
//            KafkaConfig.TOPIC_NAME_USER,
//            userMapper.toDto(user)
//        );
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, SimpleUserDto>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, SimpleUserDto> result) {
//                System.out.println("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message due to : " + ex.getMessage());
//            }
//        });
//
//        return new GenericRestResponse<>(
////                true,
////                "Success",
////                HttpStatus.OK.value()
////            );
//    }

}
