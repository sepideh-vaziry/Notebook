package com.sepideh.notebook.messagequeue;

import com.sepideh.notebook.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaMessageListener {

    @KafkaListener(topics = KafkaConfig.TOPIC_NAME_TEST, groupId = KafkaConfig.GROUP_ID)
    public void listenOnTestTopic(String message) {
        System.out.println("Received Message in groupTest : " + message);
    }

}
