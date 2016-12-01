package ru.horoshiki.crm.site.event;


import ru.horoshiki.crm.site.model.entity.User;

public class AuthorizationUserEvent extends AbstractUserEvent {
    public AuthorizationUserEvent(Object source, User user, String userIp) {
        super(source, user, userIp);
    }
}
