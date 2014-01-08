package com.dota2trade.dao;

import com.dota2trade.model.Statistic;

import java.util.List;

/**
 * Created by Cancy on 14-1-6.
 */
public interface StatisticsDao {
    public List<Statistic> getStatistics();
    public List<Statistic> getStatisticsLimit1Week();
    public List<Statistic> getStatisticsLimit1Month();
    public List<Statistic> getStatisticsLimitHalfYear();
    public List<Statistic> getStatisticsLimit1Year();
    public List<Integer> getSum(String column);
}
