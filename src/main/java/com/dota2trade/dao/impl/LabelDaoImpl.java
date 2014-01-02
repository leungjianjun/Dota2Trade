package com.dota2trade.dao.impl;

import com.dota2trade.dao.LabelDao;
import com.dota2trade.model.Label;
import com.dota2trade.model.LabelLiterature;
import com.dota2trade.model.Literature;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by teireien on 14-1-1.
 */
@Repository
public class LabelDaoImpl extends JdbcDaoSupport implements LabelDao{
    @Autowired
    public LabelDaoImpl(DataSource dataSource){
        setDataSource(dataSource);
    }
    @Override
    public int addLabel(final Label label) {
        String selectSql="SELECT * FROM label WHERE name=?";
        //先检查是否有重名的标签
        List<Label> labels =  this.getJdbcTemplate().query(selectSql,
                new Object[]{label.getName()},
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Label l = new Label();
                        l.setId(rs.getInt("id"));
                        l.setName(rs.getString("name"));
                        return l;
                    }
                }
        );

        if(labels.size()!=0){//已有同名存在
            return labels.get(0).getId();
        }else{//没有同名，需要新插入一条
            final String sql="INSERT INTO label (name,createid) VALUES (?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, label.getName());
                    ps.setInt(2, label.getCreateid());
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
    public boolean addLiteratureLabel(LabelLiterature labelLiterature) {
        String sql0 = "SELECT count(*) from label_literature where literatureid="+labelLiterature.getLiteratureid()+
                " and labelid="+labelLiterature.getLabelid()+
                " and userid="+labelLiterature.getUserid();
        int count = this.getJdbcTemplate().queryForInt(sql0);
        if(count>0){
            return false;
        }
        String sql="INSERT INTO label_literature (literatureid,labelid,userid) VALUES (?,?,?)";
        int updateCount=this.getJdbcTemplate().update(sql,labelLiterature.getLiteratureid(),labelLiterature.getLabelid(),labelLiterature.getUserid());
        if(updateCount>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Label getLabelByid(int id) {
        String sql = "SELECT * FROM label "+
                "WHERE label.id='"+id+"'";
        return (Label)this.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper(Label.class));
    }

    @Override
    public List<Label> getLabelListByLiteratureId(int literatureid) {
        String sql="SELECT * FROM label_literature " +
                "WHERE label_literature.literatureid='"+literatureid+"'";
        List<LabelLiterature> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(LabelLiterature.class));
        List<Label> ret = new ArrayList<Label>();
        for(int i=0;i<list.size();i++){
            int labelId = list.get(i).getLabelid();
            ret.add(this.getLabelByid(labelId));
        }
        return ret;
    }

    @Override
    public List<Label> getLabelListByuserId(int userid) {
        String sql="SELECT distinct(labelid) FROM label_literature " +
                "WHERE label_literature.userid='"+userid+"'";
        List<Integer> list=this.getJdbcTemplate().queryForList(sql,Integer.class);
        List<Label> ret = new ArrayList<Label>();
        for(int i=0;i<list.size();i++){
            int labelId = list.get(i);
            ret.add(this.getLabelByid(labelId));
        }
        return ret;
    }

    @Override
    public List<LabelLiterature> getLiteratureListByLabelId(int labelid) {
        String sql="SELECT * FROM label_literature " +
                "WHERE label_literature.labelid='"+labelid+"'";
        List<LabelLiterature> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(LabelLiterature.class));
        return list;
    }

    @Override
    public List<Label> getCommonLabelList() {
        String sql = "select *,count(*) as count from label_literature group by labelid order by count desc limit 6";
        List<LabelLiterature> list=this.getJdbcTemplate().query(sql,new RowMapper<LabelLiterature>() {
            @Override
            public LabelLiterature mapRow(ResultSet resultSet, int i) throws SQLException {
                LabelLiterature labelLiterature=new LabelLiterature();
                labelLiterature.setId(resultSet.getInt("id"));
                labelLiterature.setLiteratureid(resultSet.getInt("literatureid"));
                labelLiterature.setLabelid(resultSet.getInt("labelid"));
                labelLiterature.setUserid(resultSet.getInt("userid"));
                return labelLiterature;
            }
        });
        List<Label> ret = new ArrayList<Label>();
        for(int i=0;i<list.size();i++){
            int labelId = list.get(i).getLabelid();
            ret.add(this.getLabelByid(labelId));
        }
        return ret;
    }

    @Override
    public List<Label> getAllLabels() {
        String sql="SELECT * FROM label";
        List<Label> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(Label.class));
        return list;
    }
}
