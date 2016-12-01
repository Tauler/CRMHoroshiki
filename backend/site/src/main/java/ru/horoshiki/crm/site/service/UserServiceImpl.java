package ru.horoshiki.crm.site.service;

import org.springframework.transaction.annotation.Transactional;
import ru.horoshiki.crm.site.RegexpValues;
import ru.horoshiki.crm.site.dao.UserDAO;
import ru.horoshiki.crm.site.model.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by bogdanovkp on 28.11.2016.
 */
public class UserServiceImpl extends AbstractService<User, UserDAO> implements UserService {
    @Override
    @Transactional
    public User getUserByLogin(String login, String... properties) {
        return getDao().getUserByLogin(login,properties);
    }

    @Override
    @Transactional
    public boolean isUserByLogin(String login, boolean isBlank) {
        return getDao().isUserByLogin(login, isBlank);
    }

    /**
     * Метод проверки валидности пароля пользователя
     * @param password Пароль
     * @return true - валиден, false - невалиден
     */
    @Override
    public boolean checkPassword(String password) {
        Pattern p = Pattern.compile(RegexpValues.PASSWORD_REGEXP);
        Matcher m = p.matcher(password);
        if(m.matches())
            return true;
        else
            return false;
    }
}
