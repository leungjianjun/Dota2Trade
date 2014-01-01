package com.dota2trade.dao.impl;

import com.dota2trade.dao.CommentDao;
import com.dota2trade.model.Comment;
import com.dota2trade.model.CommentAttribute;
import com.dota2trade.model.ComplexComment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Created by Cancy on 14-1-1.
 */
@Repository
public class CommentDaoImpl extends JdbcDaoSupport implements CommentDao {

    @Autowired
    public CommentDaoImpl(DataSource dataSource){
        setDataSource(dataSource);
    }

    @Override
    public int addSimpleComment(Comment comment) {
        int id=comment.getId();
        String shortContent=comment.getShortContent();
        int literatureId=comment.getLiteratureId();
        int commenterId=comment.getCommenterId();
        int score=comment.getScore();
        Date commentTime=comment.getCommentTime();
        int status=comment.getStatus();
        /**判断是否已经存在**/
        if(id>0){
            //已经存在，说明这是一次修改，新插入的无id，默认为0
            String sql="UPDATE comment SET short_content='"+shortContent+"',"
                    +"commenttime='"+commentTime+"',"
                    +"score="+score+","
                    +"status="+status;
            int r=this.getJdbcTemplate().update(sql);
            if(r>0){
                return id;
            }
            System.out.println("更新失败！");
            return -1;
        }
        /**不存在，新插入**/
        String sql="INSERT into comment(commenterid,literatureid,short_content,score,status,commenttime) values("
                +commenterId+","
                +literatureId+",'"
                +shortContent+"',"
                +score+","
                +status+",'"
                +commentTime+"')";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updateCount=this.getJdbcTemplate().update(sql,keyHolder);
        if(updateCount>0){
            return keyHolder.getKey().intValue();
        }else{
            return -1;
        }
    }

    @Override
    public boolean deleteSimpleComment(int id) {
        String sql="DELETE FROM comment WHERE id='"+id+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public List<Comment> getAllSimpleCommentByLiteratureId(int literatureId, int status) {
        String sql="SELECT * from comment where literatureid="+literatureId+" AND status="+status;
        List<Comment> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(Comment.class));
        return list;
    }

    @Override
    public List<Comment> getAllSimpleCommentByUserId(int userId, int status) {
        String sql="SELECT * from comment where commenterid="+userId+" AND status="+status;
        List<Comment> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(Comment.class));
        return list;
    }

    @Override
    public List<Comment> getAllSimpleCommentByUserIdAndLiteratureId(int userId, int literatureId, int status) {
        String sql="SELECT * from comment where commenterid="+userId+" AND literatureId="+literatureId+" AND status="+status;
        List<Comment> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(Comment.class));
        return list;
    }

    @Override
    public int getScoreByLiteratureId(int literatureId) {
        String sql="SELECT AVG(score) from comment where literatireid="+literatureId+" AND score > 0";
        int avg=this.getJdbcTemplate().queryForInt(sql);
        return avg;
    }

    @Override
    public int addComplexCommentAttribute(CommentAttribute commentAttribute) {
        int id=commentAttribute.getId();
        int commenterId=commentAttribute.getCommenterId();

        return 0;
    }

    @Override
    public boolean deleteComplexCommentAttribute(int id) {
        return false;
    }

    @Override
    public List<Integer> addComplexComment(ComplexComment complexComment) {
        return null;
    }

    @Override
    public boolean deleteComplexComment(int literatureId, int userId, List<Integer> idList) {
        return false;
    }

    @Override
    public List<ComplexComment> getAllComplexCommentByLiteratureId(int literatureId, int status) {
        return null;
    }

    @Override
    public List<ComplexComment> getAllComplexCommentByUserId(int userId, int status) {
        return null;
    }

    @Override
    public List<ComplexComment> getAllComplexCommentByUserIdAndLiteratureId(int userId, int literatureId, int status) {
        return null;
    }
}
