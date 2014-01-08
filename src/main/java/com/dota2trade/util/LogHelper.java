package com.dota2trade.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

<<<<<<< HEAD
=======
import java.text.DateFormat;
>>>>>>> 8ce6d6aea5a12bac2d737053311791047924fd78
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
<<<<<<< HEAD
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        log.warn("<&&"+format1.format(cal.getTime())+"&&"+name+"&&"+action+"&&>");
    }

    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        System.out.println(format1.format(cal.getTime()));
=======
        cal.getTime();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");

        log.warn("<&&"+DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime())+"&&"+format.format(cal.getTime())+"&&"+name+"&&"+action+"&&>");
>>>>>>> 8ce6d6aea5a12bac2d737053311791047924fd78
    }
}
