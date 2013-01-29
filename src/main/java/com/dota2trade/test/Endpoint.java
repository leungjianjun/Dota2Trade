package com.dota2trade.test;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-1-3
 * Time: 下午7:58
 */
public class Endpoint {

    static final String DEFAULT_ALIAS = "ext1";
    private final String url;
    private final String alias = "ext1";
    private final long expired;

    public Endpoint(String url, long maxAgeInMilliSeconds) {
        if (url==null)
            throw new NullPointerException("Url is null.");
        this.url = url;
        //this.alias = alias;
        this.expired = System.currentTimeMillis() + maxAgeInMilliSeconds;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expired;
    }

    public String getUrl(){
        return this.url;
    }

    /**
     * Get extension alias of this end point.
     */
    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("Endpoint [uri:")
                .append(url)
                .append(", expired:")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expired))
                .append(']');
        return sb.toString();
    }

}
