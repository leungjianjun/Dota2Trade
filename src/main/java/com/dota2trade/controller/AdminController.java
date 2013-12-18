package com.dota2trade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-12-18
 * Time: 下午4:34
 */
@Controller
public class AdminController {

    @RequestMapping(value="/admin.html",method= RequestMethod.GET)
    public String group(ModelMap model){
        return "admin";
    }

    @RequestMapping(value="/paperConfig.html",method= RequestMethod.GET)
    public String paperConfig(ModelMap model){
        return "paperConfig";
    }
}
