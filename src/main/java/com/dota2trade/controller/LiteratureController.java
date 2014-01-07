package com.dota2trade.controller;

import com.dota2trade.dao.*;
import com.dota2trade.model.*;
import com.dota2trade.model.search.ComplexCondition;
import com.dota2trade.model.search.Indexer;
import com.dota2trade.model.search.Searcher;
import com.dota2trade.util.FileUploadHelper;
import com.dota2trade.util.LogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import com.dota2trade.security.SAuthentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private Searcher searcher;
    private LabelDao labelDao;
    private CommentDao commentDao;
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
        LogHelper.addLog(sAuthentication.getAccount(),"添加新的文献"+title);
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
        paper.setLink(LINK_PREFIX+"paper/"+paperFileName);
        FileUploadHelper.uploadFile(fileAttachment, paperFileName, "paper");
        paper.setCreatorid(userid);
        paper.setType(0);
        attachmentList.add(paper);

        if (!otherAttachment1.isEmpty()){
            String other1Name = new String (otherAttachment1.getOriginalFilename().getBytes ("iso-8859-1"), "UTF-8");
            String otherFile1Name = System.currentTimeMillis()+other1Name;
            FileUploadHelper.uploadFile(otherAttachment1, otherFile1Name,"other");
            attachment1=new Attachment();
            attachment1.setName(other1Name);
            attachment1.setLink(LINK_PREFIX + "other/" + otherFile1Name);
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
            attachment2.setLink(LINK_PREFIX+"other/"+otherFile2Name);
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
            attachment3.setLink(LINK_PREFIX+"other/"+otherFile3Name);
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
            attachment4.setLink(LINK_PREFIX+"other/"+otherFile4Name);
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
        model.addAttribute("literatureMetaList",literatureDao.getAllLiteratureMeta());

        int id=literatureDao.createLiterature(literature);

        Indexer indexer=new Indexer();
        indexer.indexPaper(paperFileName,id);

        System.out.println("result:"+id);
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        Map<Integer,String> map_name = new HashMap<Integer,String>();
        List<CiteRelationship> temp = literatureDao.getAllCiteRelationshipByLiteratureId(id);
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

        return "/editCite";
    }

    /**个人信息*/
    @RequestMapping(value = "/profile.html" , method = RequestMethod.GET)
    public String getProfile( @ModelAttribute("sauthentication") SAuthentication sAuthentication,Model model){
        //评分
        Map<Integer,String> score = new HashMap<Integer,String>();
        String score_str = "";
        //文献类型
        List<LiteratureType> typeList=configDao.getAllLiteratureTypes();
        Map<Integer,String> typeName = new HashMap<Integer, String>();
        for(int i=0;i<typeList.size();i++){
            typeName.put(typeList.get(i).getId(),typeList.get(i).getName());
        }
        model.addAttribute("typeList",typeName);
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        List<LiteratureMeta> literatureMetaList=literatureDao.getAllLiteratureMetaByUserid(userid);
        List<Literature> literatureList = new ArrayList<Literature>();
        for(int i=0;i<literatureMetaList.size();i++){
            LiteratureMeta meta = literatureMetaList.get(i);
            Literature literature = literatureDao.getLiteratureById(meta.getLiteratureid());
            literatureList.add(literature);
            int temp = commentDao.getScoreByLiteratureId(meta.getLiteratureid());
            if(temp==0){
                score_str="-";
            }
            else{
                score_str=Integer.toString(temp);
            }
            score.put(meta.getLiteratureid(),score_str);
        }
        //个人信息
        if(userDao.getUserInfoByUserId(userid).getName()==null){
            model.addAttribute("sign",0);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(userid);
            userInfo.setAccount(sAuthentication.getAccount());
            userInfo.setName("尚未设置");
            userInfo.setMajor("尚未设置");
            userInfo.setEmail("尚未设置");
            model.addAttribute("userInfo",userInfo);
        }else{
            model.addAttribute("sign",1);
            model.addAttribute("userInfo",userDao.getUserInfoByUserId(userid));
        }
        model.addAttribute("score",score);
        model.addAttribute("literatureList",literatureList);
        //正式简单评论
        model.addAttribute("simpleComment",commentDao.getAllSimpleCommentByUserId(userid,1));
        // 简单评论草稿
        model.addAttribute("simpleDraft",commentDao.getAllSimpleCommentByUserId(userid,0));
        //正式复杂评论
        model.addAttribute("complexComment",commentDao.getAllComplexCommentByUserId(userid,1));
        //复杂评论草稿
        model.addAttribute("complexDraft",commentDao.getAllComplexCommentByUserId(userid,0));
        return "profile";
    }
    @RequestMapping(value = "/doEditInfo",method = RequestMethod.POST)
    public String editInfo(
            @RequestParam("name") String name,
            @RequestParam("major") String major,
            @RequestParam("email") String email,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            Model model){
        LogHelper.addLog(sAuthentication.getAccount(),"修改个人信息"+name);
        UserInfo userInfo = new UserInfo();
        String account = sAuthentication.getAccount();
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        userInfo.setUserid(userid);
        userInfo.setAccount(account);
        userInfo.setEmail(email);
        userInfo.setMajor(major);
        userInfo.setName(name);
        userDao.addUserInfo(userInfo);
        return getProfile(sAuthentication,model);
    }
    @RequestMapping(value = "checkPw",method = RequestMethod.POST)
    public void checkPw(
            @RequestParam("oldPass") String oldPass,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            HttpServletResponse response,
            Model model
    ) throws IOException{
        if(userDao.getUser(sAuthentication.getAccount(),oldPass)==null){
            response.getWriter().print("false");
        }
        else{
            response.getWriter().print("true");
        }
    }
    @RequestMapping(value = "/doEditPw",method = RequestMethod.POST)
    public String editPw(
            @RequestParam("oldpassword") String oldpassword,
            @RequestParam("newpassword") String newpassword,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            Model model
    ){
        User user = userDao.getUser(sAuthentication.getAccount(),oldpassword);
        user.setPassword(newpassword);
        userDao.updateUser(user);
        return "login";
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
            ModelMap model) throws IOException {
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
        LiteratureMeta literatureMeta=literatureDao.getLiteratureMetaByLiteratureId(literatureid);
        LogHelper.addLog(sAuthentication.getAccount(),"修改文献"+literatureMeta.getTitle());
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
                otherAttachment.setLink(LINK_PREFIX+"other/"+otherFile1Name);
                otherAttachment.setCreatorid(userid);
                otherAttachment.setType(1);
                otherAttachment.setLiteratureid(literatureid);
                attachmentList.add(otherAttachment);
            }
        }
        Literature literature=literatureDao.getLiteratureById(literatureid);
        literature.setUpdaterid(userid);
        literature.setLiteratureMeta(literatureMeta);
        literature.setPublisher(publisher);
        literature.setAttachmentList(attachmentList);
        literature.setLiteratureAttributeList(newLiteratureAttributeList);
        literatureDao.updateLiterature(literature);

        return listLiterature(model);
    }
    @RequestMapping(value="/searchLiterature.html",method=RequestMethod.GET)
    public String searchLiterature(ModelMap model){
        model.addAttribute("labels",labelDao.getAllLabels());
        return "searchLiterature";
    }
    @RequestMapping(value="/searchResult.html",method=RequestMethod.GET)
    public String searchResult(
            @RequestParam("words") String words,
            ModelMap model)throws IOException{
        //words = new String(words.getBytes("iso-8859-1"), "UTF-8");
        List<Literature> list = new ArrayList<Literature>();
        long begin = System.currentTimeMillis();
        list= searcher.simpleSearch(words);
        long end = System.currentTimeMillis();
        double cost = (end-begin)/1000.0;
        model.addAttribute("cost",cost);
        model.addAttribute("literatureList",list);
        return "searchResult";
    }

    @RequestMapping(value="/doComplexSearch",method=RequestMethod.POST)
    public String doComplexSearch(
            @RequestParam("radio") int radio,
            @RequestParam("allKeywordsHave") String allKeywordsHave,
            @RequestParam("phraseHave") String phraseHave,
            @RequestParam("oneOrMoreKeywordHave") String oneOrMoreKeywordHave,
            @RequestParam("noKeywordHave") String noKeywordHave,
            @RequestParam("keywordsLocated") String keywordsLocated,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("begin") String begin,
            @RequestParam("end") String end,
            @RequestParam("labelid") int labelid,
            ModelMap model
    ){
        List<Literature> list = new ArrayList<Literature>();
        double cost=0.0;
        if(radio==0){
            ComplexCondition complexCondition=new ComplexCondition();
            complexCondition.setAllKeywordsHave(allKeywordsHave);
            complexCondition.setPhraseHave(phraseHave);
            complexCondition.setOneOrMoreKeywordHave(oneOrMoreKeywordHave);
            complexCondition.setNoKeywordHave(noKeywordHave);
            complexCondition.setKeywordsLocated(keywordsLocated);
            complexCondition.setAuthor(author);
            complexCondition.setPublisher(publisher);
            complexCondition.setBegin(begin);
            complexCondition.setEnd(end);
            System.out.println(allKeywordsHave+ " "+keywordsLocated);
            long start = System.currentTimeMillis();
            list= searcher.complexSearch(complexCondition);
            long finish = System.currentTimeMillis();
            cost = (finish-start)/1000.0;
        }else{
            List<LabelLiterature> list_temp = labelDao.getLiteratureListByLabelId(labelid);
            for(int i=0;i<list_temp.size();i++){
                list.add(literatureDao.getLiteratureById(list_temp.get(i).getLiteratureid()));
            }
        }

        model.addAttribute("cost",cost);
        model.addAttribute("literatureList",list);
        return "searchResult";
    }
    @RequestMapping(value="/literatureDetail.html",method=RequestMethod.GET)
    public String literatureDetail(
            @RequestParam("id")int literatureid,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            @RequestParam("sign") int sign,
            ModelMap model){
        int userid=userDao.getIdByUserAccount(sAuthentication.getAccount());
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
        //文献对应标签
        List<Label> labelList = labelDao.getLabelListByLiteratureId(literatureid);
        //我的标签
        List<Label> myLabelList = labelDao.getLabelListByuserId(userid);
        //常用标签
        List<Label> commonLabelList = labelDao.getCommonLabelList();
        //获得所有正式简单评论
        List<Comment> simpleComments = commentDao.getAllSimpleCommentByLiteratureId(literatureid,1);
        //获得用户该文献的草稿
        List<Comment> simpleDraftList = commentDao.getAllSimpleCommentByUserIdAndLiteratureId(userid,literatureid,0);
        Comment comment = new Comment();
        if(simpleDraftList.size()!=0){
            comment = simpleDraftList.get(0);
        }
        else{
            comment.setShortContent("");
            comment.setId(-1);
            comment.setScore(0);
        }
        //获得所有正式复杂评论
        List<ComplexComment> complexComments = commentDao.getAllComplexCommentByLiteratureId(literatureid,1);
        //获得用户该文献的复杂评论草稿
        List<ComplexComment> complexDraftList = commentDao.getAllComplexCommentByUserIdAndLiteratureId(userid,literatureid,0);

        System.out.println(complexDraftList.size());
        ComplexComment complexComment0 = new ComplexComment();
        if(complexDraftList.size()!=0){
            complexComment0 = complexDraftList.get(0);
        }
        else{
            complexComment0.setStatus(-1);
        }
        if(sign==1){
            model.addAttribute("sign",true);
        }else{
            model.addAttribute("sign",false);
        }
        model.addAttribute("simpleComments",simpleComments);
        model.addAttribute("complexComments",complexComments);
        model.addAttribute("simpleDraft",comment);
        model.addAttribute("complexDraft",complexComment0);
        model.addAttribute("labelList",labelList);
        model.addAttribute("myLabelList",myLabelList);
        model.addAttribute("commonLabelList",commonLabelList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("creator",creator);
        model.addAttribute("updater",updater);
        model.addAttribute("citelist",cite_name);
        model.addAttribute("citedlist",cited_name);
        model.addAttribute("literature",literatureDao.getLiteratureById(literatureid));
        model.addAttribute("attributeList",literatureDao.getLiteratureAttribute(literatureid));
        model.addAttribute("commentAttributeList",configDao.getAllAttributeByType(2));
        model.addAttribute("average_score",commentDao.getScoreByLiteratureId(literatureid));
        return "literatureDetail";
    }

    /**文献修改*/
    @RequestMapping(value="/listLiterature.html", method= RequestMethod.GET)
    public String listLiterature(
            ModelMap model){
        //文献类型
        List typeList=new ArrayList();
        typeList=configDao.getAllLiteratureTypes();
        model.addAttribute("typeList",typeList);
        List<LiteratureMeta> list = literatureDao.getAllLiteratureMeta();
        List<Literature> literatureList = new ArrayList<Literature>();
        for(int i=0;i<list.size();i++){
            LiteratureMeta meta = list.get(i);
            Literature literature = literatureDao.getLiteratureById(meta.getLiteratureid());
            literatureList.add(literature);
        }
        model.addAttribute("literatureList",literatureList);
        return "listLiterature";
    }

    /**
     * 添加引用关系
     */
    @RequestMapping(value = "/doaddCite",method=RequestMethod.POST)
    public String addCite(
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            HttpServletRequest request,
            ModelMap model){
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
        return listLiterature(model);
    }

    /**
     * 添加标签
     */
    @RequestMapping(value = "/doaddLabel",method=RequestMethod.POST)
    public String addLabel(
            @RequestParam("literatureid")int literatureid,
            @RequestParam("tags") String tags,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            ModelMap model) throws IOException{
        int userid = userDao.getIdByUserAccount(sAuthentication.getAccount());
        String[] args = tags.split("\\;|\\；");
        for(int i=0;i<args.length;i++){
            Label label = new Label();
            label.setCreateid(userid);
            label.setName(args[i]);
            int result = labelDao.addLabel(label);
            if(result!=-1){
                LabelLiterature labelLiterature = new LabelLiterature();
                labelLiterature.setLabelid(result);
                labelLiterature.setUserid(userid);
                labelLiterature.setLiteratureid(literatureid);
                boolean addLabelResult = labelDao.addLiteratureLabel(labelLiterature);
            }
        }
        return literatureDetail(literatureid,sAuthentication,0,model);
    }
    /**
     * 添加简单评论
     */
    @RequestMapping(value = "/doSimpleComment",method=RequestMethod.POST)
    public String addSimpleComment(
            @RequestParam("literatureid")int literatureid,
            @RequestParam("commentid") int commentid,
            @RequestParam("star") int star,
            @RequestParam("comment") String comment_text,
            @RequestParam("status") int status,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            ModelMap model) throws IOException{
        LogHelper.addLog(sAuthentication.getAccount(),"添加新评论");
        int userid = userDao.getIdByUserAccount(sAuthentication.getAccount());
        Comment new_comment = new Comment();
        new_comment.setId(commentid);
        new_comment.setCommenterId(userid);
        new_comment.setLiteratureId(literatureid);
        new_comment.setScore(star);
        new_comment.setShortContent(comment_text);
        new_comment.setStatus(status);
        commentDao.addSimpleComment(new_comment);
        return literatureDetail(literatureid,sAuthentication,0,model);
    }
    /**
     * 添加复杂评论
     */
    @RequestMapping(value = "/doAddComplexComment",method=RequestMethod.POST)
    public String addComplexComment(
            @RequestParam("literatureid")int literatureid,
            @RequestParam("status1") int status,
            @ModelAttribute("sauthentication") SAuthentication sAuthentication,
            HttpServletRequest request,
            ModelMap model) throws IOException{
        LogHelper.addLog(sAuthentication.getAccount(),"添加复杂评论");
        int userid = userDao.getIdByUserAccount(sAuthentication.getAccount());
        List<Attribute> attributeList = configDao.getAllAttributeByType(2);
        ComplexComment complexComment = new ComplexComment();
        complexComment.setCommenterId(userid);
        complexComment.setLiteratureId(literatureid);
        complexComment.setStatus(status);
//        Date ud = new java.util.Date();
//        Date sd = new java.sql.Date(ud.getTime());
//        complexComment.setCommentTime(sd);
        for(int i=0;i<attributeList.size();i++){
            Attribute attribute = attributeList.get(i);
            int commentAttributeId = Integer.parseInt(request.getParameter("commentAttribute"+attribute.getId()));
            int attributeId = Integer.parseInt(request.getParameter("attribute" + attribute.getId()));
            String attributeValue = request.getParameter("value"+attribute.getId());
            System.out.println(attributeValue);
            String attributeName = attribute.getName();
            CommentAttribute newAttribute = new CommentAttribute();
            newAttribute.setId(commentAttributeId);
            newAttribute.setAttributeId(attributeId);
            newAttribute.setAttributeName(attributeName);
            newAttribute.setValue(attributeValue);
            complexComment.addCommentAttribute(newAttribute);
        }
        commentDao.addComplexComment(complexComment);
        return literatureDetail(literatureid,sAuthentication,0,model);
    }

    /**删除简单评论*/
    @RequestMapping(value="/doDeleSimpleComment",method = RequestMethod.POST)
    public void deleteSimpleComment(
            @RequestParam("id") int id,
            HttpServletResponse response,
            Model model
    ) throws IOException{
        boolean rs = commentDao.deleteSimpleComment(id);
        response.getWriter().print(rs);
    }
    /**删除复杂评论*/
    @RequestMapping(value = "/doDeleComplexComment",method = RequestMethod.POST)
    public void deleteComplexComment(
            @RequestParam("id") String id,
            HttpServletResponse response,
            Model model
    )throws IOException{
        String[] temp = id.split("/");
        List<Integer> ids = new ArrayList<Integer>();
        for(int i=0;i<temp.length;i++){
            int id_temp = Integer.parseInt(temp[i]);
            ids.add(id_temp);
        }
        boolean rs = commentDao.deleteComplexComment(ids);
        response.getWriter().print(rs);
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
    public Searcher getSearcher(){return  searcher;}
    @Autowired
    public void setSearcher(Searcher searcher){this.searcher = searcher;}
    public LabelDao getLabelDao(){return labelDao;}
    @Autowired
    public void setLabelDao(LabelDao labelDao){this.labelDao = labelDao;}
    public CommentDao getCommentDao(){return commentDao;}
    @Autowired
    public void setCommentDao(CommentDao commentDao){this.commentDao = commentDao;}
}
