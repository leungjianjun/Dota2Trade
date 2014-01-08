package com.dota2trade.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 14-1-7
 * Time: 下午6:10
 */
public class LogHelper {

    static private Log log = LogFactory.getLog(LogHelper.class);

    public static void addLog(String name, String action){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        log.warn("<&&"+format1.format(cal.getTime())+"&&"+name+"&&"+action+"&&>");
    }

    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        System.out.println(format1.format(cal.getTime()));
    }
}
