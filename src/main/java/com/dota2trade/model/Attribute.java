package com.dota2trade.model;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-23
 * Time: 下午8:08
 *
 * @version 1.0
 * 配置属性：基本信息、引用关系、评价
 */
public class Attribute {
    private int id;
    private String name;
    private int type;//基本信息：1   评价信息：2   引用关系：3

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
