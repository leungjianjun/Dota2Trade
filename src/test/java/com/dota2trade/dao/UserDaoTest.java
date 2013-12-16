package com.dota2trade.dao;

import com.dota2trade.model.User;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-12-16
 * Time: 下午9:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/servlet-context.xml"})
public class UserDaoTest extends TestCase {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    public void testCreate(){
        User user = userDao.getUser("test","test");
        Assert.assertEquals(1,user.getId());
    }
}
