package ru.horoshiki.crm.site.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.RegexpValues;
import ru.horoshiki.crm.site.dao.UserDAO;
import ru.horoshiki.crm.site.model.entity.User;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl extends AbstractService<User, UserDAO> implements UserService {

    private SmsSender smsSender;


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

    @Override
    @Transactional
    public User getNotConfirmUserById(Long id, String... properties) {
        return getDao().getNotConfirmUserById(id, properties);
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

    @Override
    @Transactional
    public void sendConfirmSms(User user) {
        int min = 1000;
        int max = 9999;
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));

        user.setRegistrationKeyGenDate(new Date());
        user.setRegistrationKey(code);
        update(user);
        smsSender.send("7".concat(user.getLogin()), "Ваш код подтверждения регистрации: "+code);
    }

    public SmsSender getSmsSender() {
        return smsSender;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }
}
