package com.dota2trade.dao.impl;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-15
 * Time: 下午3:36
 */
@Repository
public class LiteratureDaoImpl extends JdbcDaoSupport implements LiteratureDao{

    @Autowired
    public LiteratureDaoImpl(DataSource dataSource){
        setDataSource(dataSource);
    }

    /***************************add methods*****************************/
    @Override
    public boolean createLiterature(final Literature literature) {

        final String sql="INSERT INTO literature (creatorid,status,literaturetypeid) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, literature.getCreatorid());
                ps.setInt(2, literature.getStatus());
                ps.setInt(3,literature.getLiteraturetypeid());
                return ps;
            }
        };
        //添加文档的属性信息（必须有的信息）
        int updateCount=this.getJdbcTemplate().update(preparedStatementCreator, keyHolder);

        if(updateCount>0){
            int keyid= keyHolder.getKey().intValue();//取到插入生成的id
            literature.getLiteratureMeta().setLiteratureid(keyid);
            //添加文档的基本信息（通用信息，必须有的信息）
            boolean addMetaResult=this.addLiteratureMeta(literature.getLiteratureMeta());
            if(addMetaResult==true){
                //添加出版社信息
                int publisherId= this.addPublisher(literature.getPublisher());
                if(publisherId!=-1){
                    //添加文献-出版社关系信息
                    boolean addLitPubResult=this.addLiteraturePublisher(keyid,publisherId);
                    if(addLitPubResult==true){

                        literature.getAttachment().setLiteratureid(keyid);
                        //添加文档的附件信息
                        boolean addAttachmentResult=this.addAttachment(literature.getAttachment());
                        if(addAttachmentResult==true){
                            //添加引用关系信息
                            boolean addCiteRelationshipResult=this.addCiteRelationship(literature.getCiteRelationshipList());
                            return addCiteRelationshipResult;
                        } else{
                            System.out.println("--error05--添加文档的附件信息出错！");
                            return false;
                        }
                    }else{
                        System.out.println("--error04--添加文献-出版社关系出错！");
                        return false;
                    }
                }else{
                    System.out.println("--error03--添加出版社信息出错！");
                    return false;
                }
            }else{
                System.out.println("--error02--添加文献Meta信息出错！");
                return false;
            }
        }else{
            System.out.println("--error01--添加文献属性信息出错！");
           return false;
        }
    }

    @Override
    public boolean addLiteratureMeta(LiteratureMeta literatureMeta) {
        String sql="INSERT INTO literaturemeta (literatureid,title,abstract,author,published_year,keywords,link,pages) VALUES (?,?,?,?,?,?,?,?)";
        int updateCount=this.getJdbcTemplate().update(
                sql,
                literatureMeta.getLiteratureid(),
                literatureMeta.getTitle(),
                literatureMeta.getLiterature_abstract(),
                literatureMeta.getAuthor(),
                literatureMeta.getPublished_year(),
                literatureMeta.getKey_words(),
                literatureMeta.getLink(),
                literatureMeta.getPages()
        );
        return (updateCount>0)?true:false;
    }

    @Override
    public boolean addAttachment(Attachment attachment) {
        String sql="INSERT INTO attachment (name,link,creatorid,literatureid) VALUES (?,?,?,?)";
        int updateCount=this.getJdbcTemplate().update(
                sql,
                attachment.getName(),
                attachment.getLink(),
                attachment.getCreatorid(),
                attachment.getLiteratureid()
        );
        return (updateCount>0)?true:false;
    }

    @Override
    public int addPublisher(final Publisher publisher) {

        String selectSql="SELECT * FROM publisher WHERE name=?";
        //先检查是否有重名的出版社
        List<Publisher> publishers =  this.getJdbcTemplate().query(selectSql,
                new Object[]{publisher.getName()},
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Publisher p = new Publisher();
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));

                        return p;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                }
        );

        if(publishers.size()!=0){//已有同名存在
            return publishers.get(0).getId();
        }else{//没有同名，需要新插入一条
            final String sql="INSERT INTO publisher (name) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, publisher.getName());
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
    public boolean addLiteraturePublisher(int literatureid, int publisherid) {
        String sql="INSERT INTO literature_publisher (literatureid,publisherid) VALUES (?,?)";
        int updateCount=this.getJdbcTemplate().update(sql,literatureid,publisherid);
        if(updateCount>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addCiteRelationship(List<CiteRelationship> citeRelationshipList) {
        String sql="INSERT INTO cited (literatureid,citedbyid,citedtypeid) VALUES (?,?,?)";
        boolean r=true;
        for(CiteRelationship cr:citeRelationshipList){
            int result=this.getJdbcTemplate().update(
                    sql,
                    cr.getLiteratureid(),
                    cr.getCitedbyid(),
                    cr.getCitedtypeid()
            );
            r=(r==true&&result>0);
        }
        return r;
    }

    /***************************update methods*****************************/
    @Override
    public boolean updateLiterature(Literature literature) {
        String sql="update literature set updaterid=?,status=?,literaturetypeid=? where id=?";
        int updateCount=this.getJdbcTemplate().update(
                sql,
                literature.getUpdaterid(),
                literature.getStatus(),
                literature.getLiteraturetypeid(),
                literature.getId()
        );
        return (updateCount>0)?true:false;
    }

}
