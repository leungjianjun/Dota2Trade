package com.dota2trade.controller;

import com.dota2trade.dao.ConfigDao;
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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-12-18
 * Time: 下午4:34
 */
@Controller
public class AdminController {
    private UserDao userDao;
    private ConfigDao configDao;

    @RequestMapping(value="/admin.html",method= RequestMethod.GET)
    public String admin(ModelMap model){
        model.addAttribute("userList",userDao.getAllUser());
        return "admin";
    }

    @RequestMapping(value="/paperConfig.html",method= RequestMethod.GET)
    public String paperConfig(ModelMap model){
        List typeList=configDao.getAllLiteratureTypes();
        model.addAttribute("typeList",typeList);
        model.addAttribute("size",typeList.size());

        for(int i=0;i<typeList.size();i++){
            model.addAttribute("type"+i,configDao.getAllAttributeOfLiteratureType((String)typeList.get(i)));
        }

        return "paperConfig";
    }

    @RequestMapping(value="/doUpdateUser",method=RequestMethod.POST)
    public String doUpdateUser(
            @RequestParam("id") int id,
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            Model model)throws UnsupportedEncodingException {
        boolean success=false;
        User user=new User();
        user.setId(id);
        user.setAccount(new String (account.getBytes ("iso-8859-1"), "UTF-8"));
        user.setPassword(new String (password.getBytes ("iso-8859-1"), "UTF-8"));
        if(id==-1)
            success=userDao.addUser(user);
        else
            success=userDao.updateUser(user);
        return "admin";
    }

    @RequestMapping(value="/doDeleUser",method=RequestMethod.POST)
    public String doDeleUser(@RequestParam("account") String account,Model model){
        boolean success=false;
        userDao.deleteUser(account);
        return "admin";
    }



    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public ConfigDao getConfigDao(){return configDao;}
    @Autowired
    public void setConfigDao(ConfigDao configDao){this.configDao=configDao;}

}
