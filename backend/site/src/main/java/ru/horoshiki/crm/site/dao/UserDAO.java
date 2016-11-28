package ru.horoshiki.crm.site.dao;


import ru.horoshiki.crm.site.model.User;

/**
 * Created by bogdanovkp on 28.11.2016.
 */
public interface UserDAO extends GenericDAO<User> {
    User getUserByLogin(String login, String... properties);
}
