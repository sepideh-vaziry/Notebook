package com.sepideh.notebook.service;

import com.sepideh.notebook.events.ContentCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @EventListener
    public void handleCreatedContentEvent(ContentCreatedEvent contentCreatedEvent) {
        System.out.println("content = " + contentCreatedEvent.getContent());
    }

}
