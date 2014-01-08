package com.dota2trade.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
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
        cal.getTime();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");

        log.warn("<&&"+DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime())+"&&"+format.format(cal.getTime())+"&&"+name+"&&"+action+"&&>");
    }
}
