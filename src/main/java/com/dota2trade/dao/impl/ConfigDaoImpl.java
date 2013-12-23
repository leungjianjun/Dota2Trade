package com.dota2trade.dao.impl;

import com.dota2trade.dao.ConfigDao;
import com.dota2trade.model.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by liyan.code@gmail.com
 * Date：13-12-23
 * Time: 下午8:45
 *
 * @version 1.0
 */
@Repository
public class ConfigDaoImpl extends JdbcDaoSupport implements ConfigDao {
    @Autowired
    public ConfigDaoImpl(DataSource dataSource){
        setDataSource(dataSource);
    }
    @Override
    public int addLiteratureType(String name) {
        return 0;
    }

    @Override
    public boolean deleteLiteratureType(int id) {
        return false;
    }

    @Override
    public boolean addLiteraturetypeAttribute(int literatureTypeId, Attribute attribute) {
        return false;
    }

    @Override
    public boolean changeAttributeIsmust(int literatureTypeId, int attributeId, int ismust) {
        return false;
    }

    @Override
    public boolean deleteLiteraturetypeAttribute(int literatureTypeId, Attribute attribute) {
        return false;
    }

    @Override
    public List<Attribute> getAllAttributeOfLiteratureType(int literatureTypeId) {
        return null;
    }

    @Override
    public List<Attribute> getOneAttributeOfLiteratureType(int literatureTypeId, int type) {
        return null;
    }
}
