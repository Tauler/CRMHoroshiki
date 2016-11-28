package ru.horoshiki.crm.site.service;

import org.springframework.transaction.annotation.Transactional;
import ru.horoshiki.crm.site.dao.UserDAO;
import ru.horoshiki.crm.site.model.User;


/**
 * Created by bogdanovkp on 28.11.2016.
 */
public class UserServiceImpl extends AbstractService<User, UserDAO> implements UserService {
    @Override
    @Transactional
    public User getUserByLogin(String login, String... properties) {
        return getDao().getUserByLogin(login,properties);
    }
}
