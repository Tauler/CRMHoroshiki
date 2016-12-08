package ru.horoshiki.crm.site.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.dto.UserSmallDTO;
import ru.horoshiki.crm.site.model.entity.Phone;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.service.PhoneService;
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
    @Autowired
    private PhoneService phoneService;

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public
    @ResponseBody
    BackendData getCurrentUser(){
        try {
            User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "phones", "addresses", "defaultAddress", "defaultPhone");
            if (user != null) {
                return BackendData.success(UserSmallDTO.valueOf(user));
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
    BackendData addPhone(@RequestParam(value = "phone", required = true) String phoneStr){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user==null)
            return BackendData.error("userNotFoundError");

        String code = SmsUtils.generateCode();
        Phone phone = new Phone();
        phone.setNewPhone(phoneStr);
        phone.setConfirmCode(code);
        smsSender.send("7".concat(phone.getNewPhone()), "Код подтверждения: "+code);
        phone.setUser(user);
        phoneService.update(phone);
        return BackendData.success(phone.getId());
    }

    @RequestMapping(value = "/editPhone", method = RequestMethod.GET)
    public
    @ResponseBody
    BackendData editPhone(@RequestParam(value = "phoneId", required = true) long phoneId,
                          @RequestParam(value = "phone", required = true) String newPhone){


        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "phones");
        if(user==null)
            return BackendData.error("userNotFoundError");
        Phone phone = phoneService.get(phoneId);
        if(phone==null)
            return BackendData.error("phoneNotFoundError");
        if(!user.getPhones().contains(phone))
            return BackendData.error("userNotAccessError");
        String code = SmsUtils.generateCode();
        phone.setNewPhone(HtmlUtils.htmlEscape(newPhone));
        phone.setConfirmCode(code);
        smsSender.send("7".concat(phone.getNewPhone()), "Код подтверждения: "+code);
        phone.setUser(user);
        phoneService.update(phone);

        return BackendData.success(phone.getId());
    }

    @RequestMapping(value = "/confirmPhone", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData confirmPhone(@RequestParam(value = "phoneId", required = true) long phoneId,
                             @RequestParam(value = "code", required = true) String code){
        Phone phone = phoneService.get(phoneId);
        if(phone==null)
            return BackendData.error("phoneNotFoundError");
        if(code == null || "".equals(code) || !code.equals(phone.getConfirmCode()))
            return BackendData.error("invalidCodeError");
        phone.setPhone(phone.getNewPhone());
        phone.setConfirmCode(null);
        phone.setNewPhone(null);
        phoneService.update(phone);

        return BackendData.success(true);
    }

    @RequestMapping(value = "/delPhone", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData delPhone(@RequestParam(value = "phoneId", required = true) long phoneId){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "phones");
        if(user==null)
            return BackendData.error("userNotFoundError");
        Phone phone = phoneService.get(phoneId);
        if(phone==null)
            return BackendData.error("phoneNotFoundError");
        if(!user.getPhones().contains(phone))
            return BackendData.error("userNotAccessError");
        phoneService.delete(phone);
        return BackendData.success(true);
    }
}
