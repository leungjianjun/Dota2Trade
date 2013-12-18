package com.dota2trade.controller;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.dao.UserDao;
import com.dota2trade.model.Literature;
import com.dota2trade.model.LiteratureMeta;
import com.dota2trade.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import com.dota2trade.security.SAuthentication;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:05
 */
@Controller
@SessionAttributes("sauthentication")
public class LiteratureController {
    private UserDao userDao;
    private LiteratureDao literatureDao;

    @RequestMapping(value = "/sTest.html",method = RequestMethod.GET)
    public String savepTest(@RequestParam("lid") int id,Model model){
       /* Publisher publisher=new Publisher();
        publisher.setName(name);*/
        LiteratureMeta lm=literatureDao.getLiteratureMetaByLiteratureId(id);
        /*model.addAttribute("x",x);*/
        System.out.println("lm: "+lm.getAuthor()+";"+lm.getKey_words()+";"+lm.getLiterature_abstract());
        return "/saveptest";
    }

    /**笨方法*/
    @RequestMapping(value = "/doAddLiterature",method = RequestMethod.POST)
    public String doAddLiterature(
            @RequestParam("literaturetypeid") int literaturetypeid,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("published_year") Date published_year,
            @RequestParam("pages") String pages,
            @RequestParam("literature_abstract") String literature_abstract,
            @RequestParam("key_words") String key_words,
            @RequestParam("publisher_name") String publisher_name,
            @RequestParam("link") String link,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            Model model
    ){
        LiteratureMeta literatureMeta=new LiteratureMeta();
        literatureMeta.setTitle(title);
        literatureMeta.setAuthor(author);
        literatureMeta.setPublished_year(published_year);
        literatureMeta.setPages(pages);
        literatureMeta.setLiterature_abstract(literature_abstract);
        literatureMeta.setKey_words(key_words);
        literatureMeta.setLink(link);


        Publisher publisher=new Publisher();
        publisher.setName(publisher_name);

        Literature literature=new Literature();
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        literature.setCreatorid(userid);
        literature.setUpdaterid(userid);
        literature.setStatus(0);
        literature.setLiteraturetypeid(literaturetypeid);
        literature.setLiteratureMeta(literatureMeta);
        literature.setPublisher(publisher);

        model.addAttribute("literature",literature);

        boolean r=literatureDao.createLiterature(literature);


        System.out.println("result:"+r);

        return "/editCite";
    }

    public LiteratureDao getLiteratureDao() {
        return literatureDao;
    }

    @Autowired
    public void setLiteratureDao(LiteratureDao literatureDao) {
        this.literatureDao = literatureDao;
    }
    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @RequestMapping(value="/addLiterature.html", method= RequestMethod.GET)
    public String addLiterature(ModelMap model){
        return "addLiterature";
    }
    @RequestMapping(value="/editCite.html",method=RequestMethod.GET)
    public String editCite(ModelMap model){
        return "editCite";
    }
    @RequestMapping(value="/searchLiterature.html",method=RequestMethod.GET)
    public String searchLiterature(ModelMap model){
        return "searchLiterature";
    }
    @RequestMapping(value="/searchResult.html",method=RequestMethod.GET)
    public String searchResult(ModelMap model){
        return "searchResult";
    }
    @RequestMapping(value="/literatureDetail.html",method=RequestMethod.GET)
    public String literatureDetail(ModelMap model){
        return "literatureDetail";
    }


    @RequestMapping(value="/listLiterature.html", method= RequestMethod.GET)
    public String login(ModelMap model){
        return "listLiterature";
    }
}
