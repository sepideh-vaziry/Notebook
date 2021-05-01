package com.sepideh.notebook.events;

import com.sepideh.notebook.model.Content;

public class ContentCreatedEvent extends Event {

    private Content content;

    public ContentCreatedEvent(Content content) {
        super(content);
        this.content = content;
    }

    public Content getContent() {
        return this.content;
    }

}
