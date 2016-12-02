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
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by onyushkindv on 02.12.2016.
 */
@Component
public class UserListener {

    @EventListener
    public void confirmIPAdressDevice(RegistrationUserEvent registrationUserEvent) {

    }

}
