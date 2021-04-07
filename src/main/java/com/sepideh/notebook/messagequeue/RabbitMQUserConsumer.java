package com.sepideh.notebook.messagequeue;

import com.sepideh.notebook.model.User;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQUserConsumer {

    public void handleMessage(User user) {
        System.out.println("Username = "+user.getUsername());
    }

}
