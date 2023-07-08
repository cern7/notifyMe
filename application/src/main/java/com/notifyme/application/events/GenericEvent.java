package com.notifyme.application.events;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class GenericEvent<T> extends ApplicationEvent {
    private final T type;


    public GenericEvent(final Object source, final T type) {
        super(source);
        this.type = type;

    }

    public T getType() {
        return type;
    }
}
