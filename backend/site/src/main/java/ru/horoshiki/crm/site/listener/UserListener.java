package ru.horoshiki.crm.site.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.event.RegistrationUserEvent;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

        User user = registrationUserEvent.getUser();

//        LocalDateTime date = LocalDateTime.now();
//        date.plusMinutes(5);
//        Date.from(date.atZone(ZoneId.systemDefault()).toInstant());

        user.setRegistrationKeyGenDate(new Date());
        user.setRegistrationKey(code);
        userService.update(user);
        boolean isSend = smsSender.send("7".concat(user.getLogin()), "Ваш код подтверждения регистрации: "+code);
    }

}
