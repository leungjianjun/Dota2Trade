package com.dota2trade.controller;

import com.dota2trade.model.LogContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public String home(ModelMap model) throws IOException {
        File logFile = new File("loging.log");
        BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
        String line;
        List<LogContent> contents = new ArrayList<LogContent>();
        while ((line = buffer.readLine()) != null){
            line = line.split(" - ")[1];
            if (line.charAt(0) == '<'){
                String[] con = line.split("&&");
                contents.add(new LogContent(con[1],con[2],con[3]));
                System.out.println(con[1]+" "+con[2]+" "+con[3]);
            }
        }
        model.addAttribute("logcontents",contents);
        return "index";
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String defaultHome(ModelMap model){
        return "redirect:/index.html";
    }

   /* @RequestMapping(value="/profile.html",method= RequestMethod.GET)
    public String group(ModelMap model){
        return "profile";
    }*/

    @RequestMapping(value="/login.html", method= RequestMethod.GET)
    public String login(ModelMap model){
        return "login";
    }
    @RequestMapping(value = "/dologout",method=RequestMethod.GET)
    public String logout(HttpServletRequest request,ModelMap model){
        request.getSession().setAttribute("sauthentication",null);
        return "login";
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

}
