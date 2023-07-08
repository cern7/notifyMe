package com.notifyme.application.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;


public class GenericEvent<T> implements ResolvableTypeProvider {
    private final T type;


    public GenericEvent(final T type) {
        this.type = type;
    }

    public T getType() {
        return type;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(this.type));
    }
}
