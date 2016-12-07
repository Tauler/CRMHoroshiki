package ru.horoshiki.crm.site.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.entity.Phone;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.service.UserService;
import ru.horoshiki.crm.site.utils.SmsUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by onyushkindv on 06.12.2016.
 */

@Controller("user-controller")
@RequestMapping("/user")
public class UserController {

    public static final Logger LOG=Logger.getLogger(UserController.class);

    @Autowired
    private SmsSender smsSender;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public
    @ResponseBody
    BackendData getCurrentUser(){
        try {
            User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "phones", "addresses", "defaultAddress", "defaultPhone");
            if (user != null) {
                user.setPassword("");
                user.setLogin("");
                return BackendData.success(user);
            } else {
                return BackendData.error("userNotFound");
            }
        }catch (Exception ex) {
            LOG.error(SecurityContextHolder.getContext().getAuthentication().getName() + " - " + ex.getMessage(), ex);
            return BackendData.error("error");
        }
    }

    @RequestMapping(value = "/addPhone", method = RequestMethod.GET)
    public
    @ResponseBody
    BackendData addPhone(String phoneStr){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "phones");
        if(user==null)
            return BackendData.error("userNotFoundError");

        String code = SmsUtils.generateCode();
        Phone phone = new Phone();
        phone.setPhone(phoneStr);
        phone.setConfirmCode(code);

        smsSender.send("7".concat(user.getLogin()), "Код подтверждения: "+code);

        if(user.getPhones()==null)
            user.setPhones(new HashSet<>());

        user.getPhones().add(phone);
        userService.update(user);

        return BackendData.success(phone.getId());
    }

}
