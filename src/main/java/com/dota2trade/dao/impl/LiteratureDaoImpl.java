package com.dota2trade.dao.impl;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.model.Attachment;
import com.dota2trade.model.Literature;
import com.dota2trade.model.LiteratureMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
    public boolean createLiterature(Literature literature) {
        String sql="INSERT INTO literature (creatorid,status,literaturetypeid) " +
                "VALUES (:creatorid,:status,:literaturetypeid)";
        //@see http://docs.spring.io/spring/docs/2.5.x/reference/jdbc.html
        SqlParameterSource fileParameters = new BeanPropertySqlParameterSource(literature);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updateCount=this.getJdbcTemplate().update(sql, fileParameters, keyHolder);

        if((updateCount>0)?true:false){
            int keyid= keyHolder.getKey().intValue();
            literature.getLiteratureMeta().setLiteratureid(keyid);
            boolean addMetaResult=this.addLiteratureMeta(literature.getLiteratureMeta());
            if(addMetaResult==true){
                literature.getAttachment().setLiteratureid(keyid);
                boolean addAttachmentResult=this.addAttachment(literature.getAttachment());
                if(addAttachmentResult==true){

                } else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
           return false;
        }


        return (updateCount>0)?true:false;
    }

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
        String sql="INSERT INTO attachment (name,link,creatorid,,literatureid) VALUES (?,?,?,?)";
        int updateCount=this.getJdbcTemplate().update(
                sql,
                attachment.getName(),
                attachment.getLink(),
                attachment.getCreatorid(),
                attachment.getLiteratureid()
        );
        return (updateCount>0)?true:false;
    }


}
