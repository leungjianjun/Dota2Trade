package com.dota2trade.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
        log.warn("<&&2014-01-06 15:22&&"+name+"&&"+action+"&&>");
    }
}
