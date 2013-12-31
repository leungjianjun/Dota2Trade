package com.dota2trade.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-12-18
 * Time: 下午8:12
 */
public class FileUploadHelper {
    /**
     * @param type 附件类型：paper or other
     * */
    public static void uploadFile(MultipartFile file, String filename,String type) throws IOException {
        String dir;
        if(type.equals("paper")){
            dir="attachment/paper";
        }else{
            dir="attachment/other";
        }
        File newFile = new File(dir,filename);
        newFile.createNewFile();
        OutputStream outputStream = new FileOutputStream(newFile);
        InputStream inputStream = file.getInputStream();
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        outputStream.close();
        inputStream.close();
    }
}
