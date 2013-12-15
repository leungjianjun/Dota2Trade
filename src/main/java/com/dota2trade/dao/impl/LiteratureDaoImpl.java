package com.dota2trade.dao.impl;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.model.Literature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:36
 */
public class LiteratureDaoImpl extends JdbcDaoSupport implements LiteratureDao{
    @Autowired
    public LiteratureDaoImpl(DataSource dataSource){
        setDataSource(dataSource);
    }

    @Override
    public boolean createLiterature(Literature literature, boolean isDraft) {
        String sql="insert into literature (creatorid,updaterid, ) values (?, ?)";
        this.getJdbcTemplate().update(sql);
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean updateLiterature(Literature literature, boolean isDraft) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
