package com.sepideh.notebook.messagequeue;

import com.sepideh.notebook.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_USERNAME)
public class RabbitMQUsernameConsumer {

    @RabbitHandler
    public void handleMessage(String username) {
        System.out.println("Username = "+username);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("End RabbitMQ username listener");
    }

}
