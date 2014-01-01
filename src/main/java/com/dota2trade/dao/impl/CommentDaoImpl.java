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
        final String shortContent=comment.getShortContent();
        final int literatureId=comment.getLiteratureId();
        final int commenterId=comment.getCommenterId();
        final int score=comment.getScore();
        Date commentTime=comment.getCommentTime();
        final int status=comment.getStatus();
        /**判断是否已经存在**/
        if(id>0){
            //已经存在，说明这是一次修改，新插入的无id，默认为0
            String sql="UPDATE comment SET short_content='"+shortContent+"',"
                    +"score="+score+","
                    +"status="+status
                    +"WHERE id="+id;
            int r=this.getJdbcTemplate().update(sql);
            if(r>0){
                return id;
            }
            System.out.println("更新失败！");
            return -1;
        }
        /**不存在，新插入**/
        final String sql="INSERT into comment(commenterid,literatureid,short_content,score,status) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, commenterId);
                ps.setInt(2, literatureId);
                ps.setString(3,shortContent);
                ps.setInt(4,score);
                ps.setInt(5,status);
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

    @Override
    public boolean deleteSimpleComment(int id) {
        String sql="DELETE FROM comment WHERE id='"+id+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public List<Comment> getAllSimpleCommentByLiteratureId(int literatureId, int status) {
        String sql="SELECT * from comment where literatureid="+literatureId+" AND status="+status;
        List<Comment> list=this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Comment comment=new Comment();
                        comment.setId(rs.getInt("id"));
                        comment.setStatus(rs.getInt("status"));
                        comment.setLiteratureId(rs.getInt("literatureid"));
                        comment.setCommentTime(rs.getTime("commentTime"));
                        comment.setCommenterId(rs.getInt("commenterid"));
                        comment.setScore(rs.getInt("score"));
                        comment.setCommenter(getAccountById(rs.getInt("commenterid")));
                        comment.setShortContent(rs.getString("short_content"));
                        return comment;
                    }
                }
        );
        return list;
    }

    @Override
    public List<Comment> getAllSimpleCommentByUserId(int userId, int status) {
        String sql="SELECT * from comment where commenterid="+userId+" AND status="+status;
        List<Comment> list=this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                       Comment comment=new Comment();
                        comment.setId(rs.getInt("id"));
                        comment.setStatus(rs.getInt("status"));
                        comment.setLiteratureId(rs.getInt("literatureid"));
                        comment.setCommentTime(rs.getTime("commentTime"));
                        comment.setCommenterId(rs.getInt("commenterid"));
                        comment.setScore(rs.getInt("score"));
                        comment.setCommenter(getAccountById(rs.getInt("commenterid")));
                        comment.setShortContent(rs.getString("short_content"));
                        return comment;
                    }
                }
         );
        return list;
    }

    @Override
    public List<Comment> getAllSimpleCommentByUserIdAndLiteratureId(int userId, int literatureId, int status) {
        String sql="SELECT * from comment where commenterid="+userId+" AND literatureId="+literatureId+" AND status="+status;
        List<Comment> list=this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Comment comment=new Comment();
                        comment.setId(rs.getInt("id"));
                        comment.setStatus(rs.getInt("status"));
                        comment.setLiteratureId(rs.getInt("literatureid"));
                        comment.setCommentTime(rs.getTime("commentTime"));
                        comment.setCommenterId(rs.getInt("commenterid"));
                        comment.setScore(rs.getInt("score"));
                        comment.setCommenter(getAccountById(rs.getInt("commenterid")));
                        comment.setShortContent(rs.getString("short_content"));
                        return comment;
                    }
                }
        );
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
        final int commenterId=commentAttribute.getCommenterId();
        final int literatureId=commentAttribute.getLiteratureId();
        final int attributeId=commentAttribute.getAttributeId();
        final String attributeName=commentAttribute.getAttributeName();
        final String value=commentAttribute.getValue();
        Date commentTime=commentAttribute.getCommentTime();
        final int status=commentAttribute.getStatus();
        final String sql="INSERT into commentattribute (literatureid,attributeid,attributename,value,commenterid,status) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, literatureId);
                ps.setInt(2, attributeId);
                ps.setString(3,attributeName);
                ps.setString(4, value);
                ps.setInt(5,commenterId);
                ps.setInt(6,status);
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

    @Override
    public boolean deleteComplexCommentAttribute(int id) {
        String sql="DELETE FROM commentattribute WHERE id='"+id+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public List<Integer> addComplexComment(ComplexComment complexComment) {
        List<Integer> ids=new ArrayList<Integer>();
        int literatureId=complexComment.getLiteratureId();
        int commenterId=complexComment.getCommenterId();
        String commenter=complexComment.getCommenter();
        Date commentTime=complexComment.getCommentTime();
        int status=complexComment.getStatus();
        List<CommentAttribute> list=complexComment.getCommentAttributes();
        //判断是否是已经存在的复杂评论
        if(list.get(0).getId()>0){
           //说明是已经存在的评论属性
            for(CommentAttribute commentAttribute:list){
                String sql="UPDATE commentattribute SET value='"
                        +commentAttribute.getValue()+"',commenttime='"
                        +commentTime+"',status="
                        +status
                        +" where id="+commentAttribute.getId();
                int r=this.getJdbcTemplate().update(sql);
                if(r>0){
                    ids.add(commentAttribute.getId());
                }
                System.out.println("更新失败！");
                ids.add(-1);
            }
        }
        //新插入一个复杂评论
        for(CommentAttribute commentAttribute:list){
            int id=addComplexCommentAttribute(commentAttribute);
            ids.add(id);
        }
        return ids;
    }

    @Override
    public boolean deleteComplexComment(List<Integer> idList) {
        boolean result=true;
        for(int id:idList){
            result=deleteComplexCommentAttribute(id);
            if(!result)
                break;
        }
        return result;
    }

    @Override
    public List<ComplexComment> getAllComplexCommentByLiteratureId(int literatureId, int status) {
        List<CommentAttribute> list;
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

    @Override
    public List<CommentAttribute> getAllCommentAttributeByLiteratureId(int literatureId, int status) {
        String sql="SELECT * from commentattribute where literatureid="+literatureId+" AND status="+status;
        List<CommentAttribute> list=this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        CommentAttribute commentAttribute=new CommentAttribute();
                        commentAttribute.setId(rs.getInt("id"));
                        commentAttribute.setStatus(rs.getInt("status"));
                        commentAttribute.setLiteratureId(rs.getInt("literatureid"));
                        commentAttribute.setAttributeId(rs.getInt("attributeid"));
                        commentAttribute.setAttributeName(rs.getString("attributename"));
                        commentAttribute.setCommentTime(rs.getTime("commentTime"));
                        commentAttribute.setCommenterId(rs.getInt("commenterid"));
                        commentAttribute.setValue(rs.getString("value"));
                        commentAttribute.setCommenter(getAccountById(rs.getInt("commenterid")));
                        return commentAttribute;
                    }
                }
        );
        return list;
    }

    @Override
    public List<CommentAttribute> getAllCommentAttributeByUserId(int userId, int status) {
        String sql="SELECT * from commentattribute where commenterid="+userId+" AND status="+status;
        List<CommentAttribute> list=this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        CommentAttribute commentAttribute=new CommentAttribute();
                        commentAttribute.setId(rs.getInt("id"));
                        commentAttribute.setStatus(rs.getInt("status"));
                        commentAttribute.setLiteratureId(rs.getInt("literatureid"));
                        commentAttribute.setAttributeId(rs.getInt("attributeid"));
                        commentAttribute.setAttributeName(rs.getString("attributename"));
                        commentAttribute.setCommentTime(rs.getTime("commentTime"));
                        commentAttribute.setCommenterId(rs.getInt("commenterid"));
                        commentAttribute.setValue(rs.getString("value"));
                        commentAttribute.setCommenter(getAccountById(rs.getInt("commenterid")));
                        return commentAttribute;
                    }
                }
        );
        return list;
    }

    @Override
    public List<CommentAttribute> getAllCommentAttributeByUserIdAndLiteratureId(int userId, int literatureId, int status) {
        String sql="SELECT * from commentattribute where literatureid="+literatureId+" AND commenterid="+userId+"status="+status;
        List<CommentAttribute> list=this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        CommentAttribute commentAttribute=new CommentAttribute();
                        commentAttribute.setId(rs.getInt("id"));
                        commentAttribute.setStatus(rs.getInt("status"));
                        commentAttribute.setLiteratureId(rs.getInt("literatureid"));
                        commentAttribute.setAttributeId(rs.getInt("attributeid"));
                        commentAttribute.setAttributeName(rs.getString("attributename"));
                        commentAttribute.setCommentTime(rs.getTime("commentTime"));
                        commentAttribute.setCommenterId(rs.getInt("commenterid"));
                        commentAttribute.setValue(rs.getString("value"));
                        commentAttribute.setCommenter(getAccountById(rs.getInt("commenterid")));
                        return commentAttribute;
                    }
                }
        );
        return list;
    }

    private String getAccountById(int id){
        String sql = "SELECT account FROM user WHERE id='"+id+"'";
        List<String> strLst  = getJdbcTemplate().query(sql,
                new RowMapper(){
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
                        return rs.getString(1);
                    }
                });
        return strLst.get(0);
    }
}
