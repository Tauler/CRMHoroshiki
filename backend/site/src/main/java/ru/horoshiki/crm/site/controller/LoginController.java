package ru.horoshiki.crm.site.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import ru.horoshiki.crm.sendsms.SmsSender;
import ru.horoshiki.crm.site.event.AuthorizationUserEvent;
import ru.horoshiki.crm.site.event.RegistrationUserEvent;
import ru.horoshiki.crm.site.exception.RegistrationException;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.entity.Address;
import ru.horoshiki.crm.site.model.entity.Phone;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.model.enums.PaymentMethods;
import ru.horoshiki.crm.site.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by onyushkindv on 29.11.2016.
 */
@Controller("login-controller")
@RequestMapping()
public class LoginController {
    public static final Logger LOG = Logger.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

//
//    @RequestMapping(value = "/me", method = RequestMethod.GET)
//    @ResponseBody
//    public BackendData me(){
//
//
////        User user = new User();
////        user.setName("123");
////        applicationEventPublisher.publishEvent(new RegistrationUserEvent(this,user));
////
////        System.out.println("33333");
//
//
//        LocalDateTime currentDate = LocalDateTime.now();
//        currentDate = currentDate.plusMinutes(5);
////        Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
//
//        LocalDateTime oldDate = LocalDateTime.now();
//        oldDate = oldDate.plusMinutes(10);
////        Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
//
//
//        System.out.println(currentDate.isBefore(oldDate));
//
//        return BackendData.success(true);
//    }

    /**
     * Метод авторизации в личном кабинете
     * @param request
     * @param response
     * @param username Логин
     * @param password Пароль
     * @return Возвращает BackendData
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BackendData login(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam("j_username") String username,
                             @RequestParam("j_password") String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            rememberMeServices.loginSuccess(request, response, auth);
            User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            applicationEventPublisher.publishEvent(new AuthorizationUserEvent(this, user, request.getRemoteAddr()));
            return BackendData.success(auth.isAuthenticated());
        } catch (Exception ex) {
            LOG.error(SecurityContextHolder.getContext().getAuthentication().getName() + " - " + ex.getMessage(), ex);
            return BackendData.error("error");
        }
    }

    /**
     * Метод выхода из личного кабинета
     * @param request
     * @param response
     * @return Возвращает BackendData
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public BackendData logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextHolder.getContext().setAuthentication(null);
        rememberMeServices.loginFail(request, response);
        return BackendData.success(true);
    }

    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData getStreamArchive(@RequestParam(value = "login", required = false) String login,
                                 @RequestParam(value = "isBlank", required = false) boolean isBlank)  {
        Boolean isLogin = false;
        try {
//            Pattern pattern = Pattern.compile(PATTERN_MAIL_REGEX);
//            Matcher m = pattern.matcher(email);

//            if (email != null && m.matches()) {
            isLogin = userService.isUserByLogin(login, isBlank);
            return BackendData.success(isLogin);
//            }else return BackendData.error("invalidMailFormat");
        }catch (Exception ex){
            LOG.error(SecurityContextHolder.getContext().getAuthentication().getName() + " - " + ex.getMessage(), ex);
            return BackendData.error("error");
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public BackendData saveRegistration(HttpServletRequest request,
                                        @RequestParam(value = "login", required = true) String login,
                                        @RequestParam(value = "password", required = true) String password,
                                        @RequestParam(value = "name", required = true) String name,
                                        @RequestParam(value = "mail", required = true) String mail,
                                        @RequestParam(value = "address", required = true) String address,
                                        @RequestParam(value = "intercom", required = false) String intercom,
                                        @RequestParam(value = "storey", required = false) Integer storey,
                                        @RequestParam(value = "access", required = false) Integer access,
                                        @RequestParam(value = "apartment", required = false) Integer apartment,
                                        @RequestParam(value = "comment", required = false) String comment,
                                        @RequestParam(value = "paymentMethod", required = false) String paymentMethod
                                        ) throws RegistrationException {
        if (login == null || userService.isUserByLogin(login, true)) {
            LOG.debug("Переданы не верные данные для регистрации: login - " + login);
            return BackendData.error("invalidLoginError");
        }
        if (!userService.checkPassword(password) || StringUtils.isEmpty(password)) return BackendData.error("invalidPasswordError");

        User user = new User();
        user.setLogin(login.toLowerCase());

        user.setBlank(true);
        user.setCreateDate(new Date());

        user.setName(HtmlUtils.htmlEscape(name));
        user.setMail(HtmlUtils.htmlEscape(mail));

        ArrayList<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        phone.setMain(true);
        phone.setPhone(HtmlUtils.htmlEscape(login));
        phones.add(phone);
        user.setPhones(phones);

        List<Address> addresses = new ArrayList<>();
        Address addressDef = new Address();
        addressDef.setAddress(HtmlUtils.htmlEscape(address));
        if(intercom!=null)
            addressDef.setIntercom(HtmlUtils.htmlEscape(intercom));
        if(storey!=null)
            addressDef.setStorey(storey);
        if(access!=null)
            addressDef.setAccess(access);
        if(apartment!=null)
            addressDef.setApartment(apartment);
        if(comment!=null)
            addressDef.setComment(comment);
        addressDef.setMain(true);
        addresses.add(addressDef);
        user.setAddresses(addresses);

        user.setPaymentMethodDef(PaymentMethods.valueOf(paymentMethod));

        BCryptPasswordEncoder bcript = new BCryptPasswordEncoder(5);
        user.setPassword(bcript.encode(password));

        userService.add(user);
        applicationEventPublisher.publishEvent(new RegistrationUserEvent(this,user));

        return BackendData.success(user.getId());
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.POST)
    @ResponseBody
    public BackendData registrationConfirm(HttpServletRequest request,
                                           @RequestParam(value = "userId", required = true) Long userId,
                                           @RequestParam(value = "code", required = true) String code
                                           ) {

        User user = userService.get(userId);

        if(user==null){
            return BackendData.error("userNotFoundError");
        }
        if(user.getRegistrationKeyGenDate()==null && user.getRegistrationKey()==null){
            return BackendData.error("invalidParamError");
        }

        LocalDateTime currentDate = LocalDateTime.now();
        currentDate = currentDate.minusMinutes(10);

        LocalDateTime oldDate = LocalDateTime.ofInstant(user.getRegistrationKeyGenDate().toInstant(), ZoneId.systemDefault());

        if(oldDate.isBefore(currentDate)){
            return BackendData.error("invalidExpiredDateCodeError");
        }

        if(!code.equals(user.getRegistrationKey())){
            return BackendData.error("invalidCodeError");
        }

        user.setBlank(false);
        user.setRegistrationKey(null);
        user.setRegistrationKeyGenDate(null);
        userService.update(user);

        return BackendData.success(true);
    }
}
