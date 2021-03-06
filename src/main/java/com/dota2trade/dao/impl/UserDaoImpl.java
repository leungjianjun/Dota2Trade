package com.dota2trade.dao.impl;

import com.dota2trade.dao.UserDao;
import com.dota2trade.model.User;
import com.dota2trade.model.UserInfo;
import com.dota2trade.util.HBaseTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
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

    /*
    @Override
    public boolean addUser(User user) {
        String exist="SELECT COUNT(*) FROM user WHERE account='"+user.getAccount()+"'";
        int s=this.getJdbcTemplate().queryForInt(exist);
        if(s!=0){
            //用户名已存在
            return false;
        }else{
            String sql="INSERT INTO user(account,password) VALUES(?,?)";
            int r=this.getJdbcTemplate().update(sql,user.getAccount(),user.getPassword());
            return (r>0)?true:false;
        }
    }
    */

    @Override
    public boolean deleteUser(String account) {
        String sql="DELETE FROM user WHERE account='"+account+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    /**
     * 修改密码
     * */
    public boolean updateUser(User user) {
        String sql="UPDATE user SET password='"+user.getPassword()+"' WHERE account='"+user.getAccount()+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
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

    @Override
    public List<User> getAllUser() {
        String sql="SELECT * FROM user";
        List<User> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(User.class));
        return list;
    }

    @Override
    public String getAccountById(int id) {
        String sql = "SELECT account FROM user WHERE id='"+id+"'";
        List<String> strLst  = getJdbcTemplate().query(sql,
                new RowMapper(){
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
                return rs.getString(1);
            }
        });
        return strLst.get(0);
    }

    @Override
    public UserInfo getUserInfoByUserId(int userid) {
        String sql = "SELECT * FROM user_info WHERE userid='"+userid+"'";
        UserInfo userInfo = new UserInfo();
        if(this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(UserInfo.class)).size()!=0){
            userInfo = (UserInfo)this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(UserInfo.class)).get(0);
        }

        return userInfo;
    }

    @Override
    public boolean addUserInfo(UserInfo userInfo) {
        String exist="SELECT COUNT(*) FROM user_info WHERE account='"+userInfo.getAccount()+"'";
        int s=this.getJdbcTemplate().queryForInt(exist);
        if(s!=0){
            //用户名已存在
            String sql="UPDATE user_info SET name='"+userInfo.getName()+"', email='"+userInfo.getEmail()+"', major='"+userInfo.getMajor()+"' WHERE account='"+userInfo.getAccount()+"'";
            int r=this.getJdbcTemplate().update(sql);
            return (r>0)?true:false;
        }else{
            String sql="INSERT INTO user_info(userid,account,name,major,email) VALUES(?,?,?,?,?)";
            int r=this.getJdbcTemplate().update(sql,userInfo.getUserid(),userInfo.getAccount(),userInfo.getName(),userInfo.getMajor(),userInfo.getEmail());
            return (r>0)?true:false;
        }
    }
    /**
     * MySQL数据库迁移到HBase。DAO方法需要重新实现。应该新建DAO的实现类UserDaoHbaseImpl，然后使用spring注入配置。
     * 这里由于时间关系就用quick and dirty做法，在原来的基础上快速修改一下就好。调用HBaseTable工具类快速实现。
     */

    String userTable = "user";
    String user_base_column = "user_base";
    String user_info_column = "user_info";

    @Override
    public boolean addUser(User user) {
        String rowKey = HBaseTable.uuid();
        int sqlId = HBaseTable.getTableCount(userTable);
        HBaseTable.insert(userTable,rowKey,user_base_column,"id", Bytes.toBytes(sqlId));
        HBaseTable.insert(userTable,rowKey,user_base_column,"account",user.getAccount());
        HBaseTable.insert(userTable,rowKey,user_base_column,"password",user.getPassword());
        return true;
    }

    
}