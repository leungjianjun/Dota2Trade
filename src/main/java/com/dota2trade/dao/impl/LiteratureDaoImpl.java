package com.dota2trade.dao.impl;

import com.dota2trade.dao.LiteratureDao;
import com.dota2trade.model.*;
import com.dota2trade.model.search.Indexer;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
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

    /***************************add() methods*****************************/
    @Override
    public int createLiterature(final Literature literature) {

        final String sql="INSERT INTO literature (creatorid,status,literaturetypeid,createtime) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, literature.getCreatorid());
                ps.setInt(2, literature.getStatus());
                ps.setInt(3,literature.getLiteraturetypeid());
                ps.setString(4,(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()));
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
                /************做索引**************/
                Indexer indexer=new Indexer();
                indexer.indexDB(literature.getLiteratureMeta());

                //添加出版社信息
                int publisherId= this.addPublisher(literature.getPublisher());
                if(publisherId!=-1){
                    //添加文献-出版社关系信息
                    boolean addLitPubResult=this.addLiteraturePublisher(keyid,publisherId);
                    if(addLitPubResult==true){
                        for(Attachment at:literature.getAttachmentList()){
                            at.setLiteratureid(keyid);
                        }
                        //添加文档的附件信息
                        boolean addAttachmentResult=this.addAttachment(literature.getAttachmentList());
                        if(addAttachmentResult==true){
                            for(LiteratureAttribute la:literature.getLiteratureAttributeList()){
                                la.setLiteratureid(keyid);
                            }
                            //添加文献的特殊属性
                            boolean addAttributeResult = this.addLiteratureAttribute(literature.getLiteratureAttributeList());
                            if(addAttributeResult==true){
                                System.out.println("--success06--添加文档的附加属性信息OK！");
                                return keyid;
                            }
                            else{
                                System.out.println("--success06--添加文档的附加属性信息出错！");
                                return -1;
                            }
                        } else{
                            System.out.println("--error05--添加文档的附件信息出错！");
                            return -1;
                        }
                    }else{
                        System.out.println("--error04--添加文献-出版社关系出错！");
                        return -1;
                    }
                }else{
                    System.out.println("--error03--添加出版社信息出错！");
                    return -1;
                }
            }else{
                System.out.println("--error02--添加文献Meta信息出错！");
                return -1;
            }
        }else{
            System.out.println("--error01--添加文献属性信息出错！");
           return -1;
        }
    }

    @Override
    public boolean addLiteratureMeta(LiteratureMeta literatureMeta) {
        String sql="INSERT INTO literaturemeta (literatureid,title,literature_abstract," +
                "author,published_year,key_words,link,pages) VALUES (?,?,?,?,?,?,?,?)";
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
    public boolean addAttachment(List<Attachment> attachmentList) {
        String sql="INSERT INTO attachment (name,link,creatorid,literatureid,type) VALUES (?,?,?,?,?)";
        boolean r=true;
        for(Attachment attachment:attachmentList){
            int result=this.getJdbcTemplate().update(
                    sql,
                    attachment.getName(),
                    attachment.getLink(),
                    attachment.getCreatorid(),
                    attachment.getLiteratureid(),
                    attachment.getType()
            );
            r=(r==true&&result>0);
        }
        return r;
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

                        return p;
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

    @Override
    public boolean addLiteratureAttribute(List<LiteratureAttribute> literatureAttributeList) {
        String sql = "INSERT INTO literatureattribute (literatureid,attributeid,attributename,value) VALUES (?,?,?,?)";
        boolean r = true;
        for (LiteratureAttribute la:literatureAttributeList){
            int result=this.getJdbcTemplate().update(
                    sql,
                    la.getLiteratureid(),
                    la.getAttributeid(),
                    la.getAttributename(),
                    la.getValue()
            );
            r=(r==true&&result>0);
        }
        return r;
    }

    /**********************delete() methods********************/
    @Override
    public boolean deleteLiterature(int literatureid) {

        return false;
    }

    @Override
    public boolean deleteLiteratureMeta(int literatureid) {
        String sql="DELETE FROM literaturemeta WHERE literatureid='"+literatureid+"'" ;
        int delteResult=this.getJdbcTemplate().update(sql);
        return (delteResult>0)?true:false;
    }

    @Override
    public boolean deleteAttachmentByLiteratureId(int literatureid) {
        String sql="DELETE FROM attachment WHERE literatureid='"+literatureid+"'" ;
        int delteResult=this.getJdbcTemplate().update(sql);
        return (delteResult>0)?true:false;
    }

    @Override
    public boolean deleteAttachmentByAttachmentId(int attachmentid) {
        String sql="DELETE FROM attachment WHERE id='"+attachmentid+"'" ;
        int delteResult=this.getJdbcTemplate().update(sql);
        return (delteResult>0)?true:false;
    }

    @Override
    public boolean deleteLiteraturePublisher(int literatureid) {
        String sql="DELETE FROM literature_publisher WHERE literatureid='"+literatureid+"'" ;
        int delteResult=this.getJdbcTemplate().update(sql);
        return (delteResult>0)?true:false;
    }

    @Override
    public boolean deleteCiteRelationshipById(int citeRelationshipid) {
        String sql="DELETE FROM cited WHERE id='"+citeRelationshipid+"'" ;
        int delteResult=this.getJdbcTemplate().update(sql);
        return (delteResult>0)?true:false;
    }

    @Override
    public boolean deleteACiteRelationshipByLiteratureId(int literatureid) {
        String sql="DELETE FROM cited WHERE literatureid='"+literatureid+"'" ;
        int delteResult=this.getJdbcTemplate().update(sql);
        return (delteResult>0)?true:false;
    }


    /***********************update() methods************************/
    @Override
    public boolean updateLiterature(Literature literature) {
        String sql="UPDATE literature SET " +
                //"creatorid='"+literature.getCreatorid()+"'," +
                "updaterid='"+literature.getUpdaterid()+"'," +
                //"status='"+literature.getStatus()+"'" +
                //"literaturetypeid='"+literature.getLiteraturetypeid()+"'" +
                "updatetime='"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())+"'"+
                "WHERE id='"+literature.getId()+"'";
        int r=this.getJdbcTemplate().update(sql);
        if(r>0){
            boolean r2=this.updateLiteratureMeta(literature.getLiteratureMeta());
            if(r2){
                boolean r3=this.updateLiteraturePublisher(literature.getId(),
                        this.updatePublisher(literature.getPublisher()));
                if(r3){
                    boolean r4 = this.addAttachment(literature.getAttachmentList());
                    if(r4){
                        List<LiteratureAttribute> list = literature.getLiteratureAttributeList();
                        for(int i=0;i<list.size();i++){
                            this.updateLiteratureAttribute(list.get(i));
                        }
                        return true;
                    }
                    else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public boolean updateLiteratureMeta(LiteratureMeta literatureMeta) {
        String sql="UPDATE literaturemeta SET " +
                //"title='"+literatureMeta.getTitle()+"'," +
                "literature_abstract='"+literatureMeta.getLiterature_abstract()+"'," +
                "author='"+literatureMeta.getAuthor()+"'," +
                "published_year='"+literatureMeta.getPublished_year()+"'," +
                "key_words='"+literatureMeta.getKey_words()+"'," +
                "link='"+literatureMeta.getLink()+"'," +
                "pages='"+literatureMeta.getPages()+"'" +
                "WHERE literatureid='"+literatureMeta.getLiteratureid()+"'" ;
        int updateResult=this.getJdbcTemplate().update(sql);
        return (updateResult>0)?true:false;
    }

    @Override
    public int updatePublisher(Publisher publisher) {
        return this.addPublisher(publisher);
    }

    @Override
    public boolean updateLiteraturePublisher(int literatureid, int publisherid) {
        String sql="UPDATE literature_publisher SET publisherid='"+publisherid+"' WHERE literatureid='"+literatureid+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public boolean updateAttachment(Attachment attachment) {
        String sql="UPDATE attachment SET " +
                "name='"+attachment.getName()+"' " +
                "link='"+attachment.getLink()+"'" +
                "creatorid='"+attachment.getCreatorid()+"'" +
                "literatureid='"+attachment.getLiteratureid()+"'" +
                "type='"+attachment.getType()+"'" +
                "WHERE id='"+attachment.getId()+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    @Override
    public boolean updateCiteRelationship(List<CiteRelationship> citeRelationshipList) {
        if(citeRelationshipList.size()!=0){
            int literatureid = citeRelationshipList.get(0).getLiteratureid();
            int citecount = getAllCiteRelationshipByLiteratureId(literatureid).size();
            if(citecount!=0){
                deleteACiteRelationshipByLiteratureId(literatureid);
            }
        }
        String sql_insert="INSERT INTO cited (literatureid,citedbyid,citedtypeid) VALUES (?,?,?)";
        boolean r=true;
        for(CiteRelationship citeRelationship:citeRelationshipList){
//            int updateCount=this.getJdbcTemplate().update(sql_update,
//                    citeRelationship.getCitedtypeid(),
//                    citeRelationship.getLiteratureid(),
//                    citeRelationship.getCitedbyid());
//            if(updateCount>0){
//                continue;
//            }else{
                int insertCount=this.getJdbcTemplate().update(sql_insert,
                        citeRelationship.getLiteratureid(),
                        citeRelationship.getCitedbyid(),
                        citeRelationship.getCitedtypeid());
                r=(r==true&&insertCount>0);
//            }
        }
        return r;
    }

    @Override
    public boolean updateLiteratureAttribute(LiteratureAttribute literatureAttribute) {
        String sql="UPDATE literatureattribute SET " +
                "value='"+literatureAttribute.getValue()+"' " +
                "WHERE id='"+literatureAttribute.getId()+"'";
        int r=this.getJdbcTemplate().update(sql);
        return (r>0)?true:false;
    }

    /**************************get() methods*****************************/
    @Override
    public Literature getLiteratureById(int literatureid) {
        String sql="SELECT * FROM literature WHERE id='"+literatureid+"'";
        Literature literature=(Literature)this.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper(Literature.class));
        literature.setLiteratureMeta(this.getLiteratureMetaByLiteratureId(literatureid));
        literature.setPublisher(this.getPublisherByLiteratureId(literatureid));
        literature.setAttachmentList(this.getAllAttachmentByLiteratureId(literatureid));
        literature.setCiteRelationshipList(this.getAllCiteRelationshipByLiteratureId(literatureid));

        return literature;
    }

    @Override
    public List<LiteratureMeta> getAllLiteratureMetaByUserid(int userid) {
        String sql="SELECT * FROM literaturemeta,literature " +
                "WHERE literature.creatorid='"+userid+"' AND " +
                "literaturemeta.literatureid =literature.id "; //
                //"(SELECT id FROM literature WHERE creatorid='"+userid+"'";
        List<LiteratureMeta> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(LiteratureMeta.class));
        return list;
    }

    @Override
    public LiteratureMeta getLiteratureMetaByLiteratureId(int literatureid) {
        String sql="SELECT * FROM literaturemeta WHERE literatureid='"+literatureid+"'";
        return (LiteratureMeta)this.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper(LiteratureMeta.class));
    }

    @Override
    public Publisher getPublisherByLiteratureId(int literatureid) {
        String sql="SELECT * FROM publisher,literature_publisher " +
                "WHERE literature_publisher.literatureid='"+literatureid+"' AND " +
                "publisher.id=literature_publisher.publisherid";
        return (Publisher)this.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper(Publisher.class));
    }

    @Override
    public List<Attachment> getAllAttachmentByLiteratureId(int literatureid) {
        String sql="SELECT * FROM attachment WHERE literatureid='"+literatureid+"'";
        List<Attachment> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(Attachment.class));
        return list;
    }

    @Override
    public List<CiteRelationship> getAllCiteRelationshipByLiteratureId(int literatureid) {
        String sql="SELECT * FROM cited WHERE literatureid='"+literatureid+"'";
        List<CiteRelationship> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(CiteRelationship.class));
        return list;
    }

    @Override
    public List<CiteRelationship> getAllCiteRelationshipByCitedById(int citedbyid) {
        String sql="SELECT * FROM cited WHERE citedbyid='"+citedbyid+"'";
        List<CiteRelationship> list = this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(CiteRelationship.class));
        return list;
    }

    @Override
    public List<LiteratureMeta> getAllLiteratureMeta() {
        String sql="SELECT * FROM literaturemeta";
        List<LiteratureMeta> list=this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(LiteratureMeta.class));
        return list;
    }

    @Override
    public List<Literature> getAllLiterature() {
        List<Literature> literatureList=new ArrayList<Literature>();
        String selectSql="SELECT id FROM literature";
        //先检查是否有重名的出版社
        List<Integer> allIdList =  this.getJdbcTemplate().query(selectSql,
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        int id=rs.getInt("id");
                        return new Integer(id);
                    }
                }
        );
        for(Iterator iterator=allIdList.iterator();iterator.hasNext();){
            Integer integer=(Integer)iterator.next();
            literatureList.add(this.getLiteratureById(integer.intValue()));
        }
        return literatureList;
    }

    @Override
    public List<LiteratureAttribute> getLiteratureAttribute(int literatureid) {
        String sql = "SELECT * FROM literatureattribute WHERE literatureid='"+literatureid+"'";
        List<LiteratureAttribute> list = this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(LiteratureAttribute.class));
        return list;
    }

    @Override
    public List<Integer> publishedYearIdList(String begin, String end) {

        String sql="SELECT literatureid FROM literaturemeta " +
                "WHERE published_year BETWEEN '"+begin+"' AND '"+end+"'";
        List<Integer> allIdList =  this.getJdbcTemplate().query(sql,
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        int id=rs.getInt("literatureid");
                        return new Integer(id);
                    }
                }
        );
        return allIdList;

    }
}
