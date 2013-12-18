package com.dota2trade.controller;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.dao.UserDao;
import com.dota2trade.model.Literature;
import com.dota2trade.model.LiteratureMeta;
import com.dota2trade.model.Publisher;
import com.dota2trade.util.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import com.dota2trade.security.SAuthentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
            @RequestParam("fileAttachment") MultipartFile fileAttachment,
            @RequestParam("otherAttachment1") MultipartFile otherAttachment1,
            @RequestParam("otherAttachment2") MultipartFile otherAttachment2,
            @RequestParam("otherAttachment3") MultipartFile otherAttachment3,
            @RequestParam("otherAttachment4") MultipartFile otherAttachment4,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            Model model
    ) throws IOException {
        System.out.println(new String (title.getBytes ("iso-8859-1"), "UTF-8"));
        String paperFileName = System.currentTimeMillis()+fileAttachment.getOriginalFilename();
        FileUploadHelper.uploadFile(fileAttachment,paperFileName);

        if (!otherAttachment1.isEmpty()){
            String otherFile1Name = System.currentTimeMillis()+otherAttachment1.getOriginalFilename();
            FileUploadHelper.uploadFile(otherAttachment1, otherFile1Name);
        }

        if (!otherAttachment2.isEmpty()){
            String otherFile2Name = System.currentTimeMillis()+otherAttachment2.getOriginalFilename();
            FileUploadHelper.uploadFile(otherAttachment2, otherFile2Name);
        }

        if (!otherAttachment3.isEmpty()){
            String otherFile3Name = System.currentTimeMillis()+otherAttachment3.getOriginalFilename();
            FileUploadHelper.uploadFile(otherAttachment3, otherFile3Name);
        }

        if (!otherAttachment4.isEmpty()){
            String otherFile4Name = System.currentTimeMillis()+otherAttachment4.getOriginalFilename();
            FileUploadHelper.uploadFile(otherAttachment4, otherFile4Name);
        }

        LiteratureMeta literatureMeta=new LiteratureMeta();
        literatureMeta.setTitle(new String (title.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setAuthor(new String (author.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setPublished_year(published_year);
        literatureMeta.setPages(pages);
        literatureMeta.setLiterature_abstract(new String (literature_abstract .getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setKey_words(new String (key_words.getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setLink(new String (link .getBytes("iso-8859-1"), "UTF-8"));


        Publisher publisher=new Publisher();
        publisher.setName(new String (publisher_name .getBytes ("iso-8859-1"), "UTF-8"));

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

    /**个人信息*/
    @RequestMapping(value = "/profile.html" , method = RequestMethod.GET)
    public String getProfile( @ModelAttribute("sauthentication") SAuthentication sAuthentication,Model model){
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        List<LiteratureMeta> literatureMetaList=literatureDao.getAllLiteratureMetaByUserid(userid);
        model.addAttribute("literatureMetaList",literatureMetaList);
        return "profile";
    }

    @RequestMapping(value="/addLiterature.html", method= RequestMethod.GET)
    public String addLiterature(ModelMap model){
        return "addLiterature";
    }

    @RequestMapping(value="/editCite.html",method=RequestMethod.GET)
    public String editCite(@RequestParam("literatureid")int literatureid,ModelMap model){
        model.addAttribute("literature",literatureDao.getLiteratureById(literatureid));
        return "editCite";
    }
    /**修改文献基本信息页面*/
    @RequestMapping(value="/reviseLitera.html",method=RequestMethod.GET)
    public String reviseLiterature(@RequestParam("literatureid")int literatureid,ModelMap model){
        Literature literature=literatureDao.getLiteratureById(literatureid);
        SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
        String py=format.format(literature.getLiteratureMeta().getPublished_year());
        model.addAttribute("published_year",py);
        model.addAttribute("literature",literature);
        return "reviseLitera";
    }

    /**执行文献基本信息修改*/
    @RequestMapping(value="/doEditLiterature",method=RequestMethod.POST)
    public String doEditLiterature(
           @RequestParam("literatureid")int literatureid,
           @RequestParam("author") String author,
           @RequestParam("published_year") Date published_year,
           @RequestParam("pages") String pages,
           @RequestParam("literature_abstract") String literature_abstract,
           @RequestParam("key_words") String key_words,
           @RequestParam("publisher_name") String publisher_name,
           @RequestParam("link") String link,
           @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            Model model) throws UnsupportedEncodingException {

        LiteratureMeta literatureMeta=new LiteratureMeta();
       // literatureMeta.setTitle(new String (title.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setAuthor(new String (author.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setPublished_year(published_year);
        literatureMeta.setPages(pages);
        literatureMeta.setLiterature_abstract(new String (literature_abstract .getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setKey_words(new String (key_words.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setLink(new String (link .getBytes ("iso-8859-1"), "UTF-8"));


        Publisher publisher=new Publisher();
        publisher.setName(new String (publisher_name .getBytes ("iso-8859-1"), "UTF-8"));

        Literature literature=new Literature();
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        //literature.setCreatorid(userid);
        literature.setUpdaterid(userid);
        //literature.setStatus(0);
        //literature.setLiteraturetypeid(literaturetypeid);
        literature.setLiteratureMeta(literatureMeta);
        literature.setPublisher(publisher);
        literatureDao.updateLiterature(literature);

        model.addAttribute("literatureMetaList",literatureDao.getAllLiteratureMeta());
        return "listLiterature";
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

    /**文献修改*/
    @RequestMapping(value="/listLiterature.html", method= RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("literatureMetaList",literatureDao.getAllLiteratureMeta());
        return "listLiterature";
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
}
