package com.dota2trade.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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


    @RequestMapping(value="/profile.html",method= RequestMethod.GET)
    public String group(ModelMap model){
        return "profile";
    }

    @RequestMapping(value="/login.html", method= RequestMethod.GET)
    public String login(ModelMap model){
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
        OutputStream  outputStream = new FileOutputStream(newFile);
        InputStream inputStream = file.getInputStream();
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        return "no";
    }

    @RequestMapping(value = "/attachment/{fileName}.{type}", method = RequestMethod.GET,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @ResponseBody
    public FileSystemResource getFile(@PathVariable String fileName,@PathVariable String type) throws UnsupportedEncodingException {
        byte[] bytes = fileName.getBytes("UTF-8");
        String s2 = new String(bytes, "UTF-8");
        System.out.println(s2);
        return new FileSystemResource(new File("attachment/"+s2+"."+type));
    }


}
