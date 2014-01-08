package com.dota2trade.model;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj

 * Date: 14-1-7
 * Time: 下午2:04
 */
public class LogContent {
    private String time;
    private String date;
    private String name;
    private String action;

    public LogContent(String date,String time, String name, String action) {
        this.date=date;
        this.time = time;
        this.name = name;
        this.action = action;
    }

    public String getDate(){return this.date;}
    public void setDate(String date){this.date=date;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
