package com.dota2trade.dao.impl;

import com.dota2trade.dao.StatisticsDao;
import com.dota2trade.model.Statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by Cancy on 14-1-7.
 */

@Repository
public class StatisticsDaoImpl extends JdbcDaoSupport implements StatisticsDao {

    @Autowired
    public StatisticsDaoImpl(DataSource dataSource){
        setDataSource(dataSource);
    }

    @Override
    public List<Statistic> getStatistics() {
        String sql="select Userid,Account,Cliterature,CAttachment,CSimple,CComplex from statistics where Account<>'admin'";
        List<Statistic> list=this.getJdbcTemplate().query(sql,new RowMapper(){
            public Statistic mapRow(ResultSet rs, int i) throws SQLException {
                Statistic statistic=new Statistic();
                statistic.setUserId(rs.getInt("Userid"));
                statistic.setAccount(rs.getString("Account"));
                statistic.setLiteratureCount(rs.getInt("Cliterature"));
                statistic.setAttachmentCount(rs.getInt("CAttachment"));
                statistic.setSimpleCount(rs.getInt("CSimple"));
                statistic.setComplexCount(rs.getInt("CComplex"));
                return statistic;
            }
        });
        return list;
    }

    @Override
    public List<Statistic> getStatisticsLimit1Week() {
        String sql="select Userid,Account,W_Cliterature,W_CAttachment,W_CSimple,W_CComplex from statistics where Account<>'admin'";
        List<Statistic> list=this.getJdbcTemplate().query(sql,new RowMapper(){
            public Statistic mapRow(ResultSet rs, int i) throws SQLException {
                Statistic statistic=new Statistic();
                statistic.setUserId(rs.getInt("Userid"));
                statistic.setAccount(rs.getString("Account"));
                statistic.setLiteratureCount(rs.getInt("W_Cliterature"));
                statistic.setAttachmentCount(rs.getInt("W_CAttachment"));
                statistic.setSimpleCount(rs.getInt("W_CSimple"));
                statistic.setComplexCount(rs.getInt("W_CComplex"));
                return statistic;
            }
        });
        return list;
    }

    @Override
    public List<Statistic> getStatisticsLimit1Month() {
        String sql="select Userid,Account,W_Cliterature,W_CAttachment,W_CSimple,W_CComplex from statistics where Account<>'admin'";
        List<Statistic> list=this.getJdbcTemplate().query(sql,new RowMapper(){
            public Statistic mapRow(ResultSet rs, int i) throws SQLException {
                Statistic statistic=new Statistic();
                statistic.setUserId(rs.getInt("Userid"));
                statistic.setAccount(rs.getString("Account"));
                statistic.setLiteratureCount(rs.getInt("W_Cliterature"));
                statistic.setAttachmentCount(rs.getInt("W_CAttachment"));
                statistic.setSimpleCount(rs.getInt("W_CSimple"));
                statistic.setComplexCount(rs.getInt("W_CComplex"));
                return statistic;
            }
        });
        return list;
    }

    @Override
    public List<Statistic> getStatisticsLimitHalfYear() {
        String sql="select Userid,Account,H_Cliterature,H_CAttachment,H_CSimple,H_CComplex from statistics where Account<>'admin'";
        List<Statistic> list=this.getJdbcTemplate().query(sql,new RowMapper(){
            public Statistic mapRow(ResultSet rs, int i) throws SQLException {
                Statistic statistic=new Statistic();
                statistic.setUserId(rs.getInt("Userid"));
                statistic.setAccount(rs.getString("Account"));
                statistic.setLiteratureCount(rs.getInt("H_Cliterature"));
                statistic.setAttachmentCount(rs.getInt("H_CAttachment"));
                statistic.setSimpleCount(rs.getInt("H_CSimple"));
                statistic.setComplexCount(rs.getInt("H_CComplex"));
                return statistic;
            }
        });
        return list;
    }

    @Override
    public List<Statistic> getStatisticsLimit1Year() {
        String sql="select Userid,Account,Y_Cliterature,Y_CAttachment,Y_CSimple,Y_CComplex from statistics where Account<>'admin'";
        List<Statistic> list=this.getJdbcTemplate().query(sql,new RowMapper(){
            public Statistic mapRow(ResultSet rs, int i) throws SQLException {
                Statistic statistic=new Statistic();
                statistic.setUserId(rs.getInt("Userid"));
                statistic.setAccount(rs.getString("Account"));
                statistic.setLiteratureCount(rs.getInt("Y_Cliterature"));
                statistic.setAttachmentCount(rs.getInt("Y_CAttachment"));
                statistic.setSimpleCount(rs.getInt("Y_CSimple"));
                statistic.setComplexCount(rs.getInt("Y_CComplex"));
                return statistic;
            }
        });
        return list;
    }

    @Override
    public List<Integer> getSum(String column) {
        String sql="select sum("+column+") from statistics where Account<>'admin' group by Account Order by UserId";
        List list=this.getJdbcTemplate().queryForList(sql,Integer.class);
        return list;
    }

}
