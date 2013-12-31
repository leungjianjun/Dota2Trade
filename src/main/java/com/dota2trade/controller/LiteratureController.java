package com.dota2trade.controller;

import com.dota2trade.dao.ConfigDao;
import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.dao.UserDao;
import com.dota2trade.model.*;
import com.dota2trade.model.search.Indexer;
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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;

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
    private ConfigDao configDao;
    private static String LINK_PREFIX="http://localhost:8080/attachment/";

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
            @RequestParam("literaturetypeidS") String literaturetypeidS,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("published_year") String published_year,
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
            HttpServletRequest request,
            Model model
    ) throws IOException {
        String idS=literaturetypeidS.substring(5);
        int index=Integer.parseInt(idS);
        System.out.println("index:"+index);
        int literaturetypeid=configDao.getAllLiteratureTypes().get(index-1).getId();
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());

        List<Attachment> attachmentList=new ArrayList<Attachment>();
        Attachment paper,attachment1,attachment2,attachment3,attachment4;

        paper=new Attachment();
        System.out.println(new String (title.getBytes ("iso-8859-1"), "UTF-8"));
        String attachmentName = new String (fileAttachment.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
        paper.setName(attachmentName);
        String paperFileName = System.currentTimeMillis()+attachmentName;
        paper.setLink(LINK_PREFIX+paperFileName);
        FileUploadHelper.uploadFile(fileAttachment,paperFileName,"paper");
        paper.setCreatorid(userid);
        paper.setType(0);
        attachmentList.add(paper);

        if (!otherAttachment1.isEmpty()){
            String other1Name = new String (otherAttachment1.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
            String otherFile1Name = System.currentTimeMillis()+other1Name;
            FileUploadHelper.uploadFile(otherAttachment1, otherFile1Name,"other");
            attachment1=new Attachment();
            attachment1.setName(other1Name);
            attachment1.setLink(LINK_PREFIX+otherFile1Name);
            attachment1.setCreatorid(userid);
            attachment1.setType(1);
            attachmentList.add(attachment1);
        }

        if (!otherAttachment2.isEmpty()){
            String other2Name = new String (otherAttachment2.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
            String otherFile2Name = System.currentTimeMillis()+other2Name;
            FileUploadHelper.uploadFile(otherAttachment2, otherFile2Name,"other");
            attachment2=new Attachment();
            attachment2.setName(other2Name);
            attachment2.setLink(LINK_PREFIX+otherFile2Name);
            attachment2.setCreatorid(userid);
            attachment2.setType(1);
            attachmentList.add(attachment2);
        }

        if (!otherAttachment3.isEmpty()){
            String other3Name = new String (otherAttachment3.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
            String otherFile3Name = System.currentTimeMillis()+other3Name;
            FileUploadHelper.uploadFile(otherAttachment3, otherFile3Name,"other");
            attachment3=new Attachment();
            attachment3.setName(other3Name);
            attachment3.setLink(LINK_PREFIX+otherFile3Name);
            attachment3.setCreatorid(userid);
            attachment3.setType(1);
            attachmentList.add(attachment3);
        }

        if (!otherAttachment4.isEmpty()){
            String other4Name = new String (otherAttachment4.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
            String otherFile4Name = System.currentTimeMillis()+other4Name;
            FileUploadHelper.uploadFile(otherAttachment4, otherFile4Name,"other");
            attachment4=new Attachment();
            attachment4.setName(other4Name);
            attachment4.setLink(LINK_PREFIX+otherFile4Name);
            attachment4.setCreatorid(userid);
            attachment4.setType(1);
            attachmentList.add(attachment4);
        }

        LiteratureMeta literatureMeta=new LiteratureMeta();
        literatureMeta.setTitle(new String (title.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setAuthor(new String (author.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setPublished_year(new String(published_year.getBytes("iso-8859-1"),"UTF-8"));
        literatureMeta.setPages(pages);
        literatureMeta.setLiterature_abstract(new String (literature_abstract .getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setKey_words(new String (key_words.getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setLink(new String (link .getBytes("iso-8859-1"), "UTF-8"));


        Publisher publisher=new Publisher();
        publisher.setName(new String (publisher_name .getBytes ("iso-8859-1"), "UTF-8"));

        List literatureAttributeList = new ArrayList();
        int attribute_count=configDao.getAllAttributeOfLiteratureType(literaturetypeid).size();
        if(attribute_count!=0){
            for(int i=1;i<=attribute_count;i++){
                String value = request.getParameter(literaturetypeid+"_attributeValue"+i);
                value = new String(value.getBytes("iso-8859-1"), "UTF-8");
                String attribute_name = request.getParameter(literaturetypeid+"_attributeName"+i);
                attribute_name = new String(attribute_name.getBytes("iso-8859-1"), "UTF-8");
                int attribute_id = Integer.parseInt(request.getParameter(literaturetypeid+"_attributeId"+i));
                LiteratureAttribute la = new LiteratureAttribute();
                la.setAttributeid(attribute_id);
                la.setAttributename(attribute_name);
                la.setValue(value);
                literatureAttributeList.add(la);
            }
        }
        Literature literature=new Literature();

        literature.setCreatorid(userid);
        literature.setUpdaterid(userid);
        literature.setStatus(0);
        literature.setLiteraturetypeid(literaturetypeid);
        literature.setLiteratureMeta(literatureMeta);
        literature.setPublisher(publisher);
        literature.setAttachmentList(attachmentList);
        literature.setLiteratureAttributeList(literatureAttributeList);

        model.addAttribute("literature",literature);

        int id=literatureDao.createLiterature(literature);

        Indexer indexer=new Indexer();
        indexer.indexPaper(paperFileName,id);

        System.out.println("result:"+id);

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
        return "addLiterature";
    }

    @RequestMapping(value="/editCite.html",method=RequestMethod.GET)
    public String editCite(@RequestParam("literatureid")int literatureid,ModelMap model){
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        Map<Integer,String> map_name = new HashMap<Integer,String>();
        model.addAttribute("literature",literatureDao.getLiteratureById(literatureid));
        model.addAttribute("literatureMetaList",literatureDao.getAllLiteratureMeta());
        List<CiteRelationship> temp = literatureDao.getAllCiteRelationshipByLiteratureId(literatureid);
        for(int i=0;i<temp.size();i++){
            CiteRelationship cr = temp.get(i);
            if(!(map.containsKey(cr.getCitedbyid()))){
                ArrayList<Integer> typeList = new ArrayList<Integer>();
                typeList.add(cr.getCitedtypeid());
                map.put(cr.getCitedbyid(),typeList);
            }
            else{
                ArrayList<Integer> typeList = map.get(cr.getCitedbyid());
                typeList.add(cr.getCitedtypeid());
                map.put(cr.getCitedbyid(),typeList);
            }
            map_name.put(cr.getCitedbyid(),literatureDao.getLiteratureMetaByLiteratureId(cr.getCitedbyid()).getTitle());
        }
        model.addAttribute("citeTypeList",configDao.getAllAttributeByType(3));
        model.addAttribute("type",map);
        model.addAttribute("title",map_name);
        return "editCite";
    }
    /**修改文献基本信息页面*/
    @RequestMapping(value="/reviseLitera.html",method=RequestMethod.GET)
    public String reviseLiterature(@RequestParam("literatureid")int literatureid,ModelMap model){
        Literature literature=literatureDao.getLiteratureById(literatureid);
        model.addAttribute("literature",literature);
        List<LiteratureAttribute> literatureAttributeList = literatureDao.getLiteratureAttribute(literatureid);
        model.addAttribute("literatureAttributeList",literatureAttributeList);
        return "reviseLitera";
    }

    /**执行文献基本信息修改*/
    @RequestMapping(value="/doEditLiterature",method=RequestMethod.POST)
    public String doEditLiterature(
           @RequestParam("literatureid")int literatureid,
           @RequestParam("author") String author,
           @RequestParam("published_year") String published_year,
           @RequestParam("pages") String pages,
           @RequestParam("literature_abstract") String literature_abstract,
           @RequestParam("key_words") String key_words,
           @RequestParam("publisher_name") String publisher_name,
           @RequestParam("link") String link,
           @ModelAttribute("sauthentication") SAuthentication sAuthentication,
           MultipartHttpServletRequest request,
            Model model) throws IOException {
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        LiteratureMeta literatureMeta=literatureDao.getLiteratureMetaByLiteratureId(literatureid);
       // literatureMeta.setTitle(new String (title.getBytes ("iso-8859-1"), "UTF-8"));
        literatureMeta.setAuthor(new String(author.getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setPublished_year(new String(published_year.getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setPages(pages);
        literatureMeta.setLiterature_abstract(new String(literature_abstract.getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setKey_words(new String(key_words.getBytes("iso-8859-1"), "UTF-8"));
        literatureMeta.setLink(new String (link.getBytes ("iso-8859-1"), "UTF-8"));

        //修改特殊属性
        List<LiteratureAttribute> newLiteratureAttributeList = new ArrayList<LiteratureAttribute>();
        List<LiteratureAttribute> literatureAttributeList = literatureDao.getLiteratureAttribute(literatureid);
        for(int i=0;i<literatureAttributeList.size();i++){
            LiteratureAttribute la = literatureAttributeList.get(i);
            int id = la.getId();
            String value = request.getParameter("attribute" + id);
            la.setValue(new String(value.getBytes("iso-8859-1"), "UTF-8"));
            newLiteratureAttributeList.add(la);
        }
        //修改出版社
        Publisher publisher=literatureDao.getPublisherByLiteratureId(literatureid);
        publisher.setName(new String(publisher_name.getBytes("iso-8859-1"),"UTF-8"));

        //修改附件
        List<Attachment> attachmentList=new ArrayList<Attachment>();
        int num = Integer.parseInt(request.getParameter("attachment_num"));
        if(num!=0){
            for(int i=1;i<=num;i++){
                MultipartFile attachment = request.getFile("otherAttachment"+i);
                String other1Name = new String (attachment.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
                String otherFile1Name = System.currentTimeMillis()+other1Name;
                FileUploadHelper.uploadFile(attachment, otherFile1Name,"other");
                Attachment otherAttachment = new Attachment();
                otherAttachment.setName(other1Name);
                otherAttachment.setLink(LINK_PREFIX+otherFile1Name);
                otherAttachment.setCreatorid(userid);
                otherAttachment.setType(1);
                otherAttachment.setLiteratureid(literatureid);
                attachmentList.add(otherAttachment);
            }
        }
        Literature literature=literatureDao.getLiteratureById(literatureid);
        //literature.setCreatorid(userid);
        literature.setUpdaterid(userid);
        //literature.setStatus(0);
        //literature.setLiteraturetypeid(literaturetypeid);
        literature.setLiteratureMeta(literatureMeta);
        literature.setPublisher(publisher);
        literature.setAttachmentList(attachmentList);
        literature.setLiteratureAttributeList(newLiteratureAttributeList);
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
    public String literatureDetail(
            @RequestParam("id")int literatureid,
            ModelMap model){
        Map<Integer,String> cite_name = new HashMap<Integer,String>();
        Map<Integer,String> cited_name = new HashMap<Integer,String>();
        List<CiteRelationship> cite = literatureDao.getAllCiteRelationshipByLiteratureId(literatureid);
        for(int i=0;i<cite.size();i++){
            CiteRelationship cr = cite.get(i);
            cite_name.put(cr.getCitedbyid(),literatureDao.getLiteratureMetaByLiteratureId(cr.getCitedbyid()).getTitle());
        }
        List<CiteRelationship> cited = literatureDao.getAllCiteRelationshipByCitedById(literatureid);
        for(int i=0;i<cited.size();i++){
            CiteRelationship cr = cited.get(i);
            cited_name.put(cr.getLiteratureid(),literatureDao.getLiteratureMetaByLiteratureId(cr.getLiteratureid()).getTitle());
        }
        //上传人，修改人
        Literature literature = literatureDao.getLiteratureById(literatureid);
        String creator = userDao.getAccountById(literature.getCreatorid());
        String updater = "";
        if(literature.getUpdaterid()==0){
            updater = creator;
        }else{
            updater = userDao.getAccountById(literature.getUpdaterid());
        }
        //文献类型
        List typeList=new ArrayList();
        typeList=configDao.getAllLiteratureTypes();

        model.addAttribute("typeList",typeList);
        model.addAttribute("creator",creator);
        model.addAttribute("updater",updater);
        model.addAttribute("citelist",cite_name);
        model.addAttribute("citedlist",cited_name);
        model.addAttribute("literature",literatureDao.getLiteratureById(literatureid));
        model.addAttribute("attributeList",literatureDao.getLiteratureAttribute(literatureid));
        return "literatureDetail";
    }

    /**文献修改*/
    @RequestMapping(value="/listLiterature.html", method= RequestMethod.GET)
    public String listLiterature(
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            Model model){
        int userid = userDao.getIdByUserAccount(sAuthentication.getAccount());
        model.addAttribute("literatureMetaList",literatureDao.getAllLiteratureMetaByUserid(userid));
        return "listLiterature";
    }

    /**
     * 添加引用关系
     */
    @RequestMapping(value = "/doaddCite",method=RequestMethod.POST)
    public String addCite(HttpServletRequest request,Model model){
        int citeNum=Integer.parseInt(request.getParameter("citeNum"));
        int literatureid = Integer.parseInt(request.getParameter("literatureid"));
        ArrayList<CiteRelationship> list = new ArrayList<CiteRelationship>();
        for(int i=1;i<=citeNum;i++){
            String cite = "cite"+i;
            String type = "type"+i;
            int citedbyid = Integer.parseInt(request.getParameter(cite));
            String Temp[]=request.getParameterValues(type);
            for(int j=0;j<Temp.length;j++){
                CiteRelationship cr = new CiteRelationship();
                cr.setLiteratureid(literatureid);
                cr.setCitedtypeid(Integer.parseInt(Temp[j]));
                cr.setCitedbyid(citedbyid);
                list.add(cr);
            }
        }
        literatureDao.updateCiteRelationship(list);
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
    public ConfigDao getConfigDao(){return configDao;}
    @Autowired
    public void setConfigDao(ConfigDao configDao){this.configDao=configDao;}
}
