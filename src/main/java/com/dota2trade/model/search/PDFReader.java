package com.dota2trade.model.search;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-29
 * Time: 下午2:07
 *
 * @version 1.0
 */
public class PDFReader {
    /**
     * pdf 文件转 txt，内容写到同名的txt文件内
     * @param pdfName 带后缀名.pdf的文件名
     * */
    public static void pdf2Txt(String pdfName) throws Exception {
        FileInputStream fis = new FileInputStream(Util.PAPER_DIR+pdfName);

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(Util.TXT_DIR+Util.getFilenameWithnoExtension(pdfName)+".txt"));

        PDFParser p = new PDFParser(fis);
        p.parse();
        PDFTextStripper ts = new PDFTextStripper();
        String s = ts.getText(p.getPDDocument());
        writer.write(s);
        System.out.println(s);
        fis.close();
        writer.close();
    }
}
