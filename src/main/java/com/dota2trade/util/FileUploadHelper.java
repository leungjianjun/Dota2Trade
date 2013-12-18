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
    public static void uploadFile(MultipartFile file, String filename) throws IOException {
        File newFile = new File("attachment",filename);
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
