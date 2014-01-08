package com.dota2trade.controller;


import com.dota2trade.dao.StatisticsDao;
import com.dota2trade.model.Statistic;
import com.dota2trade.model.LogContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Created with IntelliJ IDEA.
 * User: ljj-lab
 * Date: 12-12-30
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class HomeController {
    private StatisticsDao statisticsDao;

    @RequestMapping(value="/index.html", method= RequestMethod.GET)
    public String home(ModelMap model) throws IOException {
        File logFile = new File("loging.log");
        BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
        String line;
        List<LogContent> contents = new ArrayList<LogContent>();
        while ((line = buffer.readLine()) != null){
            line = line.split(" - ")[1];
            if (line.charAt(0) == '<'){
                String[] con = line.split("&&");
                contents.add(new LogContent(con[1],con[2],con[3],con[4]));
                System.out.println(con[1]+" "+con[2]+" "+con[3]+" "+con[4]);
            }
        }
        model.addAttribute("logcontents",contents);
        model.addAttribute("statistics",statisticsDao.getStatisticsLimit1Week());
        model.addAttribute("literature_count",statisticsDao.getSum("Cliterature"));
        model.addAttribute("attachment_count",statisticsDao.getSum("CAttachment"));
        model.addAttribute("simple_count",statisticsDao.getSum("CSimple"));
        model.addAttribute("complex_count",statisticsDao.getSum("CComplex"));

        return "index";
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String defaultHome(ModelMap model){
        return "redirect:/index.html";
    }

    @RequestMapping(value="/login.html", method= RequestMethod.GET)
    public String login(ModelMap model){
        return "login";
    }
    @RequestMapping(value = "/dologout",method=RequestMethod.GET)
    public String logout(HttpServletRequest request,ModelMap model){
        request.getSession().setAttribute("sauthentication",null);
        return "login";
    }

    @RequestMapping(value = "/doGetStatistics",method=RequestMethod.POST)
    public void doGetStatistics(
            @RequestParam("type") int type,
            HttpServletResponse response,
            Model model)throws IOException{
        boolean success=true;
        List list=new ArrayList();
        switch (type){
            case 1:
                list=statisticsDao.getStatisticsLimit1Week();
                break;
            case 2:
                list=statisticsDao.getStatisticsLimit1Month();
                break;
            case 3:
                list=statisticsDao.getStatisticsLimitHalfYear();
                break;
            case 4:
                list=statisticsDao.getStatisticsLimit1Year();
                break;
            default:
                list=statisticsDao.getStatistics();
                break;
        }
        JSONObject jsonobj = new JSONObject();
        JSONArray jsonarray = JSONArray.fromObject(list);
        jsonobj.put("success", success);
        jsonobj.put("list",jsonarray);
        response.getWriter().print(jsonobj);
    }

    @RequestMapping(value = "/testupload.html", method = RequestMethod.GET)
    public String testupload(ModelMap model){
        return "testlogin";
    }

    @RequestMapping(value = "/douploadtest.html",method = RequestMethod.POST)
    public String dameFileUploadTest(@RequestParam("name") String name,
                                     @RequestParam("file") MultipartFile file,ModelMap model) throws IOException {
        File newFile = new File("attachment",file.getOriginalFilename());
        newFile.createNewFile();
        OutputStream outputStream = new FileOutputStream(newFile);
        InputStream inputStream = file.getInputStream();
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        return "no";
    }

    @RequestMapping(value = "/attachment/other/{fileName}.{type}", method = RequestMethod.GET,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @ResponseBody
    public FileSystemResource getOtherFile(@PathVariable String fileName,@PathVariable String type) throws UnsupportedEncodingException {
        byte[] bytes = fileName.getBytes("UTF-8");
        String s2 = new String(bytes, "UTF-8");
        System.out.println(s2);
        return new FileSystemResource(new File("attachment/other/"+s2+"."+type));
    }
    @RequestMapping(value = "/attachment/paper/{fileName}.{type}", method = RequestMethod.GET,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @ResponseBody
    public FileSystemResource getPaperFile(@PathVariable String fileName,@PathVariable String type) throws UnsupportedEncodingException {
        byte[] bytes = fileName.getBytes("UTF-8");
        String s2 = new String(bytes, "UTF-8");
        System.out.println(s2);
        return new FileSystemResource(new File("attachment/paper/"+s2+"."+type));
    }

    public StatisticsDao getStatisticsDao(){return this.statisticsDao;}
    @Autowired
    public void setStatisticsDao(StatisticsDao statisticsDao){this.statisticsDao=statisticsDao;}

}
