package com.dota2trade.dao.impl;

import com.dota2trade.dao.ConfigDao;
import com.dota2trade.model.Attribute;
import com.dota2trade.model.LiteratureType;
import com.dota2trade.model.LiteraturetypeAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
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
    public int addLiteratureType(LiteratureType literatureType) {
        final String name= literatureType.getName();
        final String sql="INSERT INTO literaturetype (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                return ps;
            }
        };
        int updateCount=this.getJdbcTemplate().update(preparedStatementCreator, keyHolder);
        if(updateCount>0){
            return keyHolder.getKey().intValue();//取到插入生成的id
        }else{
            return -1;
        }
    }

    @Override
    public boolean deleteLiteratureType(int id) {
        String sql="DELETE FROM literaturetype WHERE id='"+id+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public boolean deleteLiteratureType(String typename) {
        int id=this.getTypeIdByName(typename);
        return deleteLiteratureType(id);
    }

    @Override
    public List<LiteratureType> getAllLiteratureTypes() {
        String sql="SELECT * from literaturetype";
        List typeList=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(LiteratureType.class));
        return typeList;
    }

    @Override
    public boolean addLiteraturetypeAttribute(int literatureTypeId, Attribute attribute) {
        int attributeid=this.addAttribute(attribute);
        String s="select count(*) from literaturetype_attribute where literaturetypeid="+literatureTypeId+" and attributeid='"+attributeid+"'";
        int count=this.getJdbcTemplate().queryForInt(s);
        if(count>=1)
            return false;
        String sql="INSERT INTO literaturetype_attribute(literaturetypeid," +
                "attributeid,ismust) VALUES(?,?,?)";
        int r=this.getJdbcTemplate().update(sql,literatureTypeId,
                attributeid,1);

        return (r>0)?true:false;
    }

    @Override
    public boolean addLiteraturetypeAttribute(String literatureTypeName, Attribute attribute) {
        int literaturetypeId=this.getTypeIdByName(literatureTypeName);
        return  addLiteraturetypeAttribute(literaturetypeId,attribute);
    }

    @Override
    public int addAttribute(final Attribute attribute) {
        String selectSql="SELECT * FROM attribute WHERE name=? AND type=?";
        //先检查是否有重名的
        List<Attribute> attributes =  this.getJdbcTemplate().query(selectSql,
                new Object[]{attribute.getName(),attribute.getType()},
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Attribute a = new Attribute();
                        a.setId(rs.getInt("id"));
                        a.setName(rs.getString("name"));
                        a.setType(rs.getInt("type"));
                        return a;
                    }
                }
        );

        if(attributes.size()!=0){//已有同名存在
            return attributes.get(0).getId();
        }else{//没有同名，需要新插入一条
            final String sql="INSERT INTO attribute (name,type) VALUES (?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, attribute.getName());
                    ps.setInt(2, attribute.getType());
                    return ps;
                }
            };
            int updateCount=this.getJdbcTemplate().update(preparedStatementCreator,keyHolder);
            if(updateCount>0){
                return keyHolder.getKey().intValue();
            }else{
                return -1;
            }
        }
    }

    @Override
    public boolean deleteAttribute(String name, int type) {
        String sql="DELETE FROM attribute " +
                "WHERE name='"+name+"' AND type='"+type+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }


    @Override
    public boolean changeAttributeIsmust(int literatureTypeId, int attributeId, int ismust) {
        String sql="UPDATE literaturetype_attribute SET ismust='"+ismust+"' " +
                "WHERE literaturetypeid='"+literatureTypeId+"' AND attributeid='"+attributeId+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public boolean changeAttributeIsmust(String literatureTypeName, String attributeName, int ismust) {
        String s="select id from attribute where name='"+attributeName+"'";
        int attrubuteId=this.getJdbcTemplate().queryForInt(s);
        int literatureTypeId=this.getTypeIdByName(literatureTypeName);
        return changeAttributeIsmust(literatureTypeId,attrubuteId,ismust);
    }

    @Override
    public boolean deleteLiteraturetypeAttribute(int literatureTypeId, Attribute attribute) {
        String s="select id from attribute where name='"+attribute.getName()+"'";
        int attrubuteId=this.getJdbcTemplate().queryForInt(s);
        String sql="DELETE FROM literaturetype_attribute " +
                "WHERE literaturetypeid='"+literatureTypeId+"' AND attributeid='"+attrubuteId+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public boolean deleteLiteraturetypeAttribute(String literatureTypeName, Attribute attribute) {
        int id=this.getTypeIdByName(literatureTypeName);
        return deleteLiteraturetypeAttribute(id,attribute);
    }

    @Override
    public List<LiteraturetypeAttribute> getAllAttributeOfLiteratureType(int literatureTypeId) {
        String selectSql="SELECT * FROM attribute WHERE id IN ( " +
                "SELECT attributeid FROM literaturetype_attribute WHERE literaturetypeid='"+literatureTypeId+"')";

        List<Attribute> attributeList=this.getJdbcTemplate().query(selectSql,new BeanPropertyRowMapper(Attribute.class));

        String sql="SELECT * FROM literaturetype_attribute WHERE literaturetypeid='"+literatureTypeId+"'";
        List<LiteraturetypeAttribute> literaturetypeAttributeList =  this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        LiteraturetypeAttribute literaturetypeAttribute = new LiteraturetypeAttribute();
                        literaturetypeAttribute.setIsmust(rs.getInt("ismust"));
                        return literaturetypeAttribute;
                    }
                }
        );
        int size=literaturetypeAttributeList.size();
        if(attributeList.size()==size){
            for(int i=0;i<size;i++){
                literaturetypeAttributeList.get(i).setLiteraturetypeid(literatureTypeId);
                literaturetypeAttributeList.get(i).setAttribute(attributeList.get(i));
            }
            return literaturetypeAttributeList;
        }else{
            return null;
        }
    }

    @Override
    public List<LiteraturetypeAttribute> getAllAttributeOfLiteratureType(String literatureTypeName){
        int literaturetypeId=this.getTypeIdByName(literatureTypeName);
        return getAllAttributeOfLiteratureType(literaturetypeId);
    }

    @Override
    public List<LiteraturetypeAttribute> getOneAttributeOfLiteratureType(int literatureTypeId, int type) {
        String selectSql="SELECT * FROM attribute WHERE id IN ( " +
                "SELECT attributeid FROM literaturetype_attribute " +
                "WHERE literaturetypeid='"+literatureTypeId+"' AND attributetype='"+type+"')";

        List<Attribute> attributeList=this.getJdbcTemplate().query(selectSql,new BeanPropertyRowMapper(Attribute.class));

        String sql="SELECT * FROM literaturetype_attribute " +
                "WHERE literaturetypeid='"+literatureTypeId+"' AND attributetype='"+type+"'";
        List<LiteraturetypeAttribute> literaturetypeAttributeList =  this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        LiteraturetypeAttribute literaturetypeAttribute = new LiteraturetypeAttribute();
                        literaturetypeAttribute.setIsmust(rs.getInt("ismust"));
                        return literaturetypeAttribute;
                    }
                }
        );
        int size=literaturetypeAttributeList.size();
        if(attributeList.size()==size){
            for(int i=0;i<size;i++){
                literaturetypeAttributeList.get(i).setLiteraturetypeid(literatureTypeId);
                literaturetypeAttributeList.get(i).setAttribute(attributeList.get(i));
            }
            return literaturetypeAttributeList;
        }else{
            return null;
        }
    }

    @Override
    public List<Attribute> getAllAttributeByType(int type) {
        String sql="select * from attribute where type="+type;
        List<Attribute> attributeList=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(Attribute.class));
        return attributeList;
    }

    private int getTypeIdByName(String literatureTypeName){
        String sql="SELECT id from literaturetype where name = '"+literatureTypeName+"'";
        int literaturetypeId=this.getJdbcTemplate().queryForInt(sql);
        return literaturetypeId;
    }
}
