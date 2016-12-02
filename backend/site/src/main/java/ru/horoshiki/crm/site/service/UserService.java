package ru.horoshiki.crm.site.service;

import ru.horoshiki.crm.site.model.entity.User;

/**
 * Created by bogdanovkp on 28.11.2016.
 */
public interface UserService extends GenericService<User> {
    User getUserByLogin(String login, String... properties);
    boolean isUserByLogin(String login, boolean isBlank);
    User getNotConfirmUserById(Long id, String... properties);


    boolean checkPassword(String password);
    void sendConfirmSms(User user);
}
