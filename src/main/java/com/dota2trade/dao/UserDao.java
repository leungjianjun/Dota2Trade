package com.dota2trade.dao;

import com.dota2trade.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ljj-lab
 * Date: 12-12-30
 * Time: 下午8:24
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    public User getUser(String account,String password);
}
