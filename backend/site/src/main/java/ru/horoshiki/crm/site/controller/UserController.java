package ru.horoshiki.crm.site.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.service.UserService;

/**
 * Created by onyushkindv on 06.12.2016.
 */

@Controller("user-controller")
@RequestMapping("/user")
public class UserController {

    public static final Logger LOG=Logger.getLogger(UserController.class);

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
}
