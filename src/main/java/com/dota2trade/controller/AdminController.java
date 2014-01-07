package com.dota2trade.controller;

import com.dota2trade.dao.ConfigDao;
import com.dota2trade.dao.UserDao;
import com.dota2trade.model.Attribute;
import com.dota2trade.model.LiteratureType;
import com.dota2trade.model.LiteraturetypeAttribute;
import com.dota2trade.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


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
        List typeList=new ArrayList();
        typeList=configDao.getAllLiteratureTypes();
        model.addAttribute("typeList",typeList);
        List typeAttributeList=new ArrayList();
        for(int i=0;i<typeList.size();i++){
            LiteratureType type= (LiteratureType) typeList.get(i);
            int id=type.getId();
            typeAttributeList.add(configDao.getAllAttributeOfLiteratureType(id));
        }
        model.addAttribute("typeAttributeList",typeAttributeList);
        model.addAttribute("basicAttributeList",configDao.getAllAttributeByType(1));
        model.addAttribute("commentAttributeList",configDao.getAllAttributeByType(2));
        model.addAttribute("citeAttributeList",configDao.getAllAttributeByType(3));
        return "paperConfig";
    }
    @RequestMapping(value="/doChangeMust",method=RequestMethod.POST)
    public void doChangeMust(
            @RequestParam("args1[]") String[] args1,
            @RequestParam("args2[]") String[] args2,
            @RequestParam("typename") String typename,
            HttpServletResponse response,
            Model model)throws IOException{
        boolean success=true;
        for(int i=0;i<args1.length;i++){
             success=configDao.changeAttributeIsmust(typename,args1[i],0);
            if(success==false)
                break;
        }
        for(int i=0;i<args2.length;i++){
            success=configDao.changeAttributeIsmust(typename,args2[i],1);
            if(success==false)
                break;
        }
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", success);
        response.getWriter().print(jsonobj);
    }

    @RequestMapping(value="/doAddConfigs",method=RequestMethod.POST)
    public void doAddConfigs(
            @RequestParam("type") String type,
            @RequestParam("args[]") String[] args,
            @RequestParam("typename") String typename,
            HttpServletResponse response,
            Model model)throws UnsupportedEncodingException,IOException{
        boolean success=true;
        if(type.equals("types")){
            for(int i=0;i<args.length;i++){
                LiteratureType ltype=new LiteratureType();
                ltype.setName(args[i]);
                int result=configDao.addLiteratureType(ltype);
                if(result<=0){
                    success=false;
                    break;
                }
            }
        }
        else if(type.equals("type_attributes")){
            for(int i=0;i<args.length;i++){
                LiteraturetypeAttribute ltypeAttr=new LiteraturetypeAttribute();
                Attribute attr=new Attribute();
                attr.setName(args[i]);
                attr.setType(1);
                success=configDao.addLiteraturetypeAttribute(typename, attr);
                if(success==false)
                    break;
            }
        }else if(type.equals("comment_attributes")){
            for(int i=0;i<args.length;i++){
                Attribute attr=new Attribute();
                attr.setName(args[i]);
                attr.setType(2);
                int result=configDao.addAttribute(attr);
                if(result<=0){
                    success=false;
                    break;
                }
            }
        }else if(type.equals("cite_attributes")){
            for(int i=0;i<args.length;i++){
                Attribute attr=new Attribute();
                attr.setName(args[i]);
                attr.setType(3);
                int result=configDao.addAttribute(attr);
                if(result<=0){
                    success=false;
                    break;
                }
            }
        }
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", success);
        response.getWriter().print(jsonobj);
    }

    @RequestMapping(value="/doDeleteConfigs",method=RequestMethod.POST)
    public void doDeleteConfigs(
            @RequestParam("type") String type,
            @RequestParam("chk_value[]") String[] chk_value,
            @RequestParam("typename") String typename,
            HttpServletResponse response,
            Model model)throws IOException{
        boolean success=true;
        if(type.equals("types")){
            success=configDao.deleteLiteratureType(typename);
        }
        else if(type.equals("type_attributes")){
            for(int i=0;i<chk_value.length;i++){
                Attribute attr=new Attribute();
                attr.setName(chk_value[i]);
                attr.setType(1);
                success=configDao.deleteLiteraturetypeAttribute(typename,attr);
                if(success==false)
                    break;
            }

        }else if(type.equals("comment_attributes")){
            for(int i=0;i<chk_value.length;i++){
                success=configDao.deleteAttribute(chk_value[i],2);
                if(success==false)
                    break;
            }

        }else if(type.equals("cite_attributes")){
            for(int i=0;i<chk_value.length;i++){
                success=configDao.deleteAttribute(chk_value[i], 3);
                if(success==false)
                    break;
            }

        }

        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", success);
        response.getWriter().print(jsonobj);

    }

    @RequestMapping(value="/doUpdateUser",method=RequestMethod.POST)
    public void doUpdateUser(
            @RequestParam("id") int id,
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            HttpServletResponse response,
            Model model)throws UnsupportedEncodingException,IOException {
        boolean success=true;
        User user=new User();
        user.setId(id);
        user.setAccount(new String (account.getBytes ("iso-8859-1"), "UTF-8"));
        user.setPassword(new String (password.getBytes ("iso-8859-1"), "UTF-8"));
        if(id==-1)
            success=userDao.addUser(user);
        else
            success=userDao.updateUser(user);
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", success);
        response.getWriter().print(jsonobj);
    }

    @RequestMapping(value="/doDeleUser",method=RequestMethod.POST)
    public void doDeleUser(@RequestParam("account") String account,HttpServletResponse response,Model model)throws IOException{
        boolean success=false;
        success=userDao.deleteUser(account);
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", success);
        response.getWriter().print(jsonobj);
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
