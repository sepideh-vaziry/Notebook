package com.sepideh.notebook.messagequeue;

import com.sepideh.notebook.config.RabbitMQConfig;
import com.sepideh.notebook.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
public class RabbitMQUserConsumer {

    @RabbitHandler
    public void handleMessage(User user) {
        System.out.println("Username = "+user.getUsername());

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("End RabbitMQ listener");
    }

}
