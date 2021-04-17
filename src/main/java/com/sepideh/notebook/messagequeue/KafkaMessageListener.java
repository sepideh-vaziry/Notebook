package com.sepideh.notebook.messagequeue;

import com.sepideh.notebook.config.KafkaConfig;
import com.sepideh.notebook.dto.user.SimpleUserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaMessageListener {

    //******************************************************************************************************************
//    @KafkaListener(topics = KafkaConfig.TOPIC_NAME_TEST, groupId = KafkaConfig.GROUP_ID_TEST)
//    public void listenOnTestTopic(String message) {
//        System.out.println("Received Message in groupTest : " + message);
//
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
//
//        System.out.println("End Kafka listener");
//    }
//
//    //******************************************************************************************************************
//    @KafkaListener(
//        topics = KafkaConfig.TOPIC_NAME_USER,
//        groupId = KafkaConfig.GROUP_ID_USER,
//        containerFactory = "kafkaListenerContainerFactoryForUser"
//    )
//    public void listenOnUserTopic(SimpleUserDto user) {
//        System.out.println("Received Message in groupUser. User name is : " + user.getUsername());
//
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
//
//        System.out.println("End Kafka user listener");
//    }

}
