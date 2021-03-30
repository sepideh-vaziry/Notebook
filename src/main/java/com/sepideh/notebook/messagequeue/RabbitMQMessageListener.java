package com.sepideh.notebook.messagequeue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.concurrent.TimeUnit;

public class RabbitMQMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("message = " + new String(message.getBody()));

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("End");
    }

}
