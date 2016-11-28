package ru.horoshiki.crm.site.service;

import ru.horoshiki.crm.site.model.User;

/**
 * Created by bogdanovkp on 28.11.2016.
 */
public interface UserService extends GenericService<User> {
    User getUserByLogin(String login, String... properties);
}
