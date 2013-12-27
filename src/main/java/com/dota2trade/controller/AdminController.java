package com.dota2trade.controller;

import com.dota2trade.dao.UserDao;
import com.dota2trade.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-12-18
 * Time: 下午4:34
 */
@Controller
public class AdminController {
    private UserDao userDao;

    @RequestMapping(value="/admin.html",method= RequestMethod.GET)
    public String admin(ModelMap model){
        model.addAttribute("userList",userDao.getAllUser());
        return "admin";
    }

    @RequestMapping(value="/paperConfig.html",method= RequestMethod.GET)
    public String paperConfig(ModelMap model){
        return "paperConfig";
    }

    @RequestMapping(value="/doUpdateUser",method=RequestMethod.POST)
    public String doUpdateUser(
           @RequestParam(value="id") int id,
            @RequestParam(value="account") String account,
            @RequestParam(value="password") String password,
            Model model)throws UnsupportedEncodingException {
        System.out.println("hello"+id+"/"+account);
        /*
        User user=new User();
        user.setId(id);
        user.setAccount(new String (account.getBytes ("iso-8859-1"), "UTF-8"));
        user.setPassword(new String (password.getBytes ("iso-8859-1"), "UTF-8"));
        */


        return "admin";
    }


    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
