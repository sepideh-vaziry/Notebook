package com.sepideh.notebook;

import com.sepideh.notebook.events.Event;

public interface DomainEventPublisher {

    void publish(Event event);

}
