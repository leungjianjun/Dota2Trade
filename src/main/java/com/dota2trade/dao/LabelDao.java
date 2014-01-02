package com.dota2trade.dao;

import com.dota2trade.model.Label;
import com.dota2trade.model.LabelLiterature;
import com.dota2trade.model.Literature;

import java.util.List;

/**
 * Created by teireien on 14-1-1.
 */
public interface LabelDao {
    /** 添加新标签*/
    public int addLabel(Label label);
    public boolean addLiteratureLabel(LabelLiterature labelLiterature);
    /**根据labelid获得label*/
    public Label getLabelByid(int id);
    /**根据literatureid获取标签*/
    public List<Label> getLabelListByLiteratureId(int literatureid);
    /**根据userid获得他用过的标签*/
    public List<Label> getLabelListByuserId(int userid);
    /**根据labelid获得所有使用该label的literature*/
    public List<LabelLiterature> getLiteratureListByLabelId(int labelid);
    /**获得常用标签*/
    public List<Label> getCommonLabelList();
    /**获得所有标签*/
    public List<Label> getAllLabels();
}
