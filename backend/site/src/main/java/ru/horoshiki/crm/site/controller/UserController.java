package ru.horoshiki.crm.site.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.dto.UserSmallDTO;
import ru.horoshiki.crm.site.model.entity.Address;
import ru.horoshiki.crm.site.model.entity.Phone;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.model.enums.OrderConfirmType;
import ru.horoshiki.crm.site.model.enums.Sex;
import ru.horoshiki.crm.site.service.AddressService;
import ru.horoshiki.crm.site.service.PhoneService;
import ru.horoshiki.crm.site.service.UserService;
import ru.horoshiki.crm.site.utils.SmsUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    private AddressService addressService;

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

    @RequestMapping(value = "/addPhone", method = RequestMethod.POST)
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

        phoneService.update(phone);
        return BackendData.success(phone.getId());
    }

    @RequestMapping(value = "/editPhone", method = RequestMethod.POST)
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
//        if(!user.getPhones().contains(phone))
//            return BackendData.error("userNotAccessError");
        String code = SmsUtils.generateCode();
        phone.setNewPhone(HtmlUtils.htmlEscape(newPhone));
        phone.setConfirmCode(code);
        smsSender.send("7".concat(phone.getNewPhone()), "Код подтверждения: "+code);
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

    @RequestMapping(value = "/deletePhone", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData deletePhone(@RequestParam(value = "phoneId", required = true) long phoneId){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "phones");
        if(user==null)
            return BackendData.error("userNotFoundError");
        Phone phone = phoneService.get(phoneId);
        if(phone==null)
            return BackendData.error("phoneNotFoundError");
//        if(!user.getPhones().contains(phone))
//            return BackendData.error("userNotAccessError");
        phoneService.delete(phone);
        return BackendData.success(true);
    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData editPassword(@RequestParam(value = "password", required = true) String password,
                             @RequestParam(value = "newPassword", required = true) String newPassword){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if(user==null)
            return BackendData.error("userNotFoundError");

        BCryptPasswordEncoder bcript = new BCryptPasswordEncoder(5);
        if(!bcript.matches(password, user.getPassword()))
            return BackendData.error("incorrectPasswordError");

        if (!userService.checkPassword(newPassword) || StringUtils.isEmpty(newPassword))
            return BackendData.error("invalidNewPasswordError");

        user.setPassword(bcript.encode(newPassword));
        userService.update(user);

        return BackendData.success(true);
    }

    @RequestMapping(value = "/editAddress", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData editAddress(@RequestParam(value = "id", required = false) Long addressId,
                             @RequestParam(value = "address", required = true) String address,
                             @RequestParam(value = "intercom", required = false) String intercom,
                             @RequestParam(value = "storey", required = false) String storey,
                             @RequestParam(value = "access", required = false) String access,
                             @RequestParam(value = "apartment", required = false) String apartment,
                             @RequestParam(value = "comment", required = false) String comment) {
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Address addressDef = null;

        if(addressId!=null && addressId!=0){
            addressDef = addressService.get(addressId);
            if(addressDef==null)
                return BackendData.error("addressNotFoundError");
//            if(!user.getAddresses().contains(addressDef))
//                return BackendData.error("userNotAccessError");
        }else{
            addressDef = new Address();
        }
        addressDef.setAddress(HtmlUtils.htmlEscape(address));
        addressDef.setUser(user);

        if(intercom!=null && !"null".equals(intercom))
            addressDef.setIntercom(HtmlUtils.htmlEscape(intercom));
        else
            addressDef.setIntercom(null);

        if(storey!=null && !"".equals(storey) && !"null".equals(storey))
            addressDef.setStorey(Integer.valueOf(storey));
        else
             addressDef.setStorey(null);

        if(access!=null && !"".equals(access) && !"null".equals(access))
            addressDef.setAccess(Integer.valueOf(access));
        else
            addressDef.setAccess(null);

        if(apartment!=null && !"".equals(apartment) && !"null".equals(apartment))
            addressDef.setApartment(Integer.valueOf(apartment));
        else
            addressDef.setApartment(null);

        if(comment!=null)
            addressDef.setComment(comment);

      addressService.update(addressDef);

//        addressService.update(addressDef);
        return BackendData.success(true);
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData deleteAddress(@RequestParam(value = "id", required = true) long addressId) {
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "addresses");
        Address addressDef = null;
        addressDef = addressService.get(addressId);
        if (addressDef == null)
            return BackendData.error("addressNotFoundError");
//        if (!user.getAddresses().contains(addressDef))
//            return BackendData.error("userNotAccessError");
        addressService.delete(addressDef);
        return BackendData.success(true);
    }

    @RequestMapping(value = "/editAccount", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData editSex(@RequestParam(value = "sex", required = false) String sexStr,
                        @RequestParam(value = "birthday", required = false) String birthdayStr,
                        @RequestParam(value = "orderConfirm", required = false) String orderConfirm,
                        @RequestParam(value = "orderConfirmType", required = false) String orderConfirmType,
                        @RequestParam(value = "notifications", required = false) String notifications){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName(), "addresses");
        if(user==null)
            return BackendData.error("userNotFoundError");
        if(sexStr!=null && !"".equals(sexStr) && !"null".equals(sexStr))
            user.setSex(Sex.valueOf(sexStr));
        if(orderConfirm!=null && !"".equals(orderConfirm) && !"null".equals(orderConfirm))
            user.setOrderConfirm(Boolean.valueOf(orderConfirm));
        if(orderConfirmType!=null && !"".equals(orderConfirmType) && !"null".equals(orderConfirmType))
            user.setOrderConfirmType(OrderConfirmType.valueOf(orderConfirmType));
        if(birthdayStr!=null && !"".equals(birthdayStr)  && !"null".equals(birthdayStr)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime birthday = LocalDateTime.parse(birthdayStr + " 00:00:00", formatter);
            user.setBirthday(Date.from(birthday.atZone(ZoneId.systemDefault()).toInstant()));
        }
        if(notifications!=null && !"".equals(notifications) && !"null".equals(notifications))
            user.setNotifications(Boolean.valueOf(notifications));

        userService.update(user);
        return BackendData.success(true);
    }

}
