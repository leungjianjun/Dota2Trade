package com.dota2trade.dao.impl;

import com.dota2trade.dao.UserDao;
import com.dota2trade.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ljj-lab
 * Date: 12-12-30
 * Time: 下午8:25
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public boolean addUser(User user) {
        String sql="INSERT INTO user(account,password) VALUES(?,?)";
        int r=this.getJdbcTemplate().update(sql,user.getAccount(),user.getPassword());
        return (r>0)?true:false;
    }

    @Override
    public boolean deleteUser(String account) {
        String sql="DELETE FROM user WHERE account='"+account+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public boolean updateUser(User user) {
        //String sql="UPDATE user SET account='"user.getAccount()"'";
        return false;
    }

    @Override
    public User getUser(String account, String password) {
        System.out.println(account+" "+password);
        List<User> users =  this.getJdbcTemplate().query(
                "SELECT * FROM user WHERE account = ? AND password = ?",
                new Object[]{account, password},
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setAccount(rs.getString("account"));
                        user.setPassword(rs.getString("password"));
                        return user;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                }
        );
        if (users.size()==0){
            return  null;
        }else {
            return users.get(0);
        }
    }

    @Override
    public int getIdByUserAccount(String account) {
        String sql="SELECT id FROM user WHERE account='"+account+"'";

        return this.getJdbcTemplate().queryForInt(sql);
    }
}