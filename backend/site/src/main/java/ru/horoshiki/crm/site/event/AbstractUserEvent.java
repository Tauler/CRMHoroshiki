package ru.horoshiki.crm.site.event;

import org.springframework.context.ApplicationEvent;
import ru.horoshiki.crm.site.model.entity.User;


public abstract class AbstractUserEvent extends ApplicationEvent {
    private User user;
    private String userIp;

    public AbstractUserEvent(Object source, User user, String userIp) {
        super(source);
        this.user = user;
        this.userIp = userIp;
    }

    public User getUser() {
        return user;
    }

    public String getUserIp() {
        return userIp;
    }
}
