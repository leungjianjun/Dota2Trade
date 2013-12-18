package com.dota2trade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: ljj-lab
 * Date: 12-12-30
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class HomeController {

    @RequestMapping(value="/index.html", method= RequestMethod.GET)
    public String home(ModelMap model){
        return "index";
    }


   /* @RequestMapping(value="/profile.html",method= RequestMethod.GET)
    public String group(ModelMap model){
        return "profile";
    }*/

    @RequestMapping(value="/login.html", method= RequestMethod.GET)
    public String login(ModelMap model){
        return "login";
    }
}
