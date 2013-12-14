package com.dota2trade.security;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-12-14
 * Time: 下午10:19
 */
public class DomainSecurity {
    public static String[] whitelist = {"/login.html"};

    public static String permissionRedirect = "/login.html";

    public static String dologinUrl = "/dologin.html";

    public static <T> boolean contains( final T[] array, final T v ) {
        for ( final T e : array )
            if ( e == v || v != null && v.equals( e ) )
                return true;

        return false;
    }

    public static void main(String args[]){
        System.out.println(contains(whitelist,"/login.html"));
        String url = "12312312312";
        System.out.println(url.substring(url.indexOf("referer=") + 8));
    }
}
