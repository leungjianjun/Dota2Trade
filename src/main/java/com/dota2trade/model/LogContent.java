package com.dota2trade.model;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj

 * Date: 14-1-7
 * Time: 下午2:04
 */
public class LogContent {
    private String time;
    private String name;
    private String action;

    public LogContent(String time, String name, String action) {
        this.time = time;
        this.name = name;
        this.action = action;
    }

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
