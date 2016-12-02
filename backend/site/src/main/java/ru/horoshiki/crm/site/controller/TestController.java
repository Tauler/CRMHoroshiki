package ru.horoshiki.crm.site.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.service.TestDBService;


@Controller("test-controller")
@RequestMapping("")
public class TestController {
    public static final Logger LOG=Logger.getLogger(TestController.class);

    @Autowired
    private TestDBService testDBService;

    @RequestMapping(value = "/isAvailableServer", method = RequestMethod.GET)
    public
    @ResponseBody
    BackendData ping() {
        return BackendData.success(testDBService.isDBAvailable());
    }
}
