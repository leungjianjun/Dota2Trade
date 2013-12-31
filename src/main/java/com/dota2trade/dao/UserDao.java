package com.dota2trade.dao;

import com.dota2trade.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ljj-lab
 * Date: 12-12-30
 * Time: 下午8:24
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {

    boolean addUser(User user);
    boolean deleteUser(String account);
    boolean updateUser(User user);
    public User getUser(String account,String password);

    int getIdByUserAccount(String account);
    public List<User> getAllUser();
    public String getAccountById(int id);
}
