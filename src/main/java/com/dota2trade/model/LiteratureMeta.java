package com.dota2trade.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-16
 * Time: 上午10:07
 */
public class LiteratureMeta {
    private int literatureid;
    private String title;
    private String literature_abstract;//摘要
    private String author;
    private Date published_year;//发行时间
    private String key_words;//关键字
    private String link;
    private String pages;

    public int getLiteratureid() {
        return literatureid;
    }

    public void setLiteratureid(int literatureid) {
        this.literatureid = literatureid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLiterature_abstract() {
        return literature_abstract;
    }

    public void setLiterature_abstract(String literature_abstract) {
        this.literature_abstract = literature_abstract;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublished_year() {
        return published_year;
    }

    public void setPublished_year(Date published_year) {
        this.published_year = published_year;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
