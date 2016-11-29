package ru.horoshiki.crm.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by onyushkindv on 29.11.2016.
 */
@Controller("login-controller")
@RequestMapping()
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private RememberMeServices rememberMeServices;


    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public BackendData me(){
        SecurityContextHolder.getContext().setAuthentication(null);
        User user = userService.getUserByLogin("123");
        return BackendData.success(true);
    }

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
//            applicationEventPublisher.publishEvent(new AuthorizationUserEvent(this, user, request.getRemoteAddr()));
            return BackendData.success(auth.isAuthenticated());
        } catch (Exception ex) {
//            LOG.error(SecurityContextHolder.getContext().getAuthentication().getName() + " - " + ex.getMessage(), ex);
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
}
