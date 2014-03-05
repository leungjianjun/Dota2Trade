package com.dota2trade.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

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
    public static void uploadFile(MultipartFile file, String filename,String type)
            throws IOException {
        String dir;
        if(type.equals("paper")){
            dir="attachment/paper";
        }else{
            dir="attachment/other";
        }
        dir = "hdfs://localhost:9000/"+dir+filename;
        Configuration conf = new Configuration();

        InputStream inputStream = file.getInputStream();
        FileSystem fs = FileSystem.get(URI.create(dir), conf);
        OutputStream outputStream = fs.create(new Path(dir), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(inputStream, outputStream, 4096, true);
        outputStream.close();
        inputStream.close();
    }
}
