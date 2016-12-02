package ru.horoshiki.crm.site.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.event.RegistrationUserEvent;
import ru.horoshiki.crm.site.service.UserService;

import java.util.Random;

/**
 * Created by onyushkindv on 02.12.2016.
 */
@Component
public class UserListener {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsSender smsSender;

    @EventListener
    public void confirmIPAdressDevice(RegistrationUserEvent registrationUserEvent) {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(9999));

        boolean isSend = smsSender.send("7".concat(registrationUserEvent.getUser().getLogin()), "Ваш код подтверждения регистрации: "+code);
    }

}
