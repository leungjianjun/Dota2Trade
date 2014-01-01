package com.dota2trade.dao;

import com.dota2trade.model.Comment;
import com.dota2trade.model.CommentAttribute;
import com.dota2trade.model.ComplexComment;

import java.util.List;

/**
 * Created by Cancy on 14-1-1.
 */
public interface CommentDao {
/******************************简单评论******************************************/
   /**返回自增id，包含update**/
    public int addSimpleComment(Comment comment);
    public boolean deleteSimpleComment(int id);

    /**获得简单评论，status为0:草稿，1:正式**/
    public List<Comment> getAllSimpleCommentByLiteratureId(int literatureId,int status);
    public List<Comment> getAllSimpleCommentByUserId(int userId,int status);
    public List<Comment> getAllSimpleCommentByUserIdAndLiteratureId(int userId,int literatureId,int status);

    /**获得某个文献的评分**/
    public int getScoreByLiteratureId(int literatureId);

/******************************复杂评论******************************************/
    /**评论元素的操作**/
    public int addComplexCommentAttribute(CommentAttribute commentAttribute);
    public boolean deleteComplexCommentAttribute(int id);

    /**对一篇文献的复杂评论操作**/
    public List<Integer> addComplexComment(ComplexComment complexComment);
    public boolean deleteComplexComment(int literatureId,int userId,List<Integer> idList);

    /**获得复杂评论**/
    public List<ComplexComment> getAllComplexCommentByLiteratureId(int literatureId,int status);
    public List<ComplexComment> getAllComplexCommentByUserId(int userId,int status);
    public List<ComplexComment> getAllComplexCommentByUserIdAndLiteratureId(int userId,int literatureId,int status);
}
