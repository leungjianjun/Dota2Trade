package com.dota2trade.model.search;

/**
 * Created with IntelliJ IDEA.
 * User: liyan.code@gmail.com
 * Date: 13-12-26
 * Time: 下午2:37
 * 复杂检索用到的检索条件
 */
public class ComplexCondition {
    private String allKeywordsHave; //所有必须包含的关键字
    private String phraseHave;//包含的精确短语
    private String oneOrMoreKeywordHave;  //至少包含一个关键字
    private String noKeywordHave;  //不能包含的关键字
    private String keywordsLocated;//关键字位置
    private String author;
    private String publisher;
    private String begin; //出版时间开始k
    private String end;  //出版时间结束

    public String getAllKeywordsHave() {
        return allKeywordsHave;
    }

    public void setAllKeywordsHave(String allKeywordsHave) {
        this.allKeywordsHave = allKeywordsHave;
    }

    public String getPhraseHave() {
        return phraseHave;
    }

    public void setPhraseHave(String phraseHave) {
        this.phraseHave = phraseHave;
    }

    public String getOneOrMoreKeywordHave() {
        return oneOrMoreKeywordHave;
    }

    public void setOneOrMoreKeywordHave(String oneOrMoreKeywordHave) {
        this.oneOrMoreKeywordHave = oneOrMoreKeywordHave;
    }

    public String getNoKeywordHave() {
        return noKeywordHave;
    }

    public void setNoKeywordHave(String noKeywordHave) {
        this.noKeywordHave = noKeywordHave;
    }

    public String getKeywordsLocated() {
        return keywordsLocated;
    }

    public void setKeywordsLocated(String keywordsLocated) {
        this.keywordsLocated = keywordsLocated;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
