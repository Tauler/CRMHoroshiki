package ru.horoshiki.crm.site.event;

import org.springframework.context.ApplicationEvent;
import ru.horoshiki.crm.site.model.entity.User;


public class RegistrationUserEvent extends ApplicationEvent {
    private User user;

    public RegistrationUserEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
