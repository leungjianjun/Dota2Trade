package com.dota2trade.test;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 13-1-3
 * Time: 下午4:57
 */
public class Main {

    public static void main(String args[]) throws IOException {
        OpenIdManager manager = new OpenIdManager();
        manager.setReturnTo("www.woku1.com/openId");
        manager.setRealm("www.woku1.com");

        Endpoint endpoint = manager.lookupEndpoint("https://www.google.com/accounts/o8/id");
        System.out.println(endpoint);

        Association association = manager.lookupAssociation(endpoint);
        System.out.println(association);

        String url = manager.getAuthenticationUrl(endpoint, association);
        System.out.println("Copy the authentication URL in browser:\n" + url);

        System.out.println("After successfully sign on in browser, enter the URL of address bar in browser:");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String ret = bf.readLine();
        //HttpServletRequest request = createRequest(ret);
        //Authentication authentication = manager.getAuthentication(request, association.getRawMacKey());
        //System.out.println(authentication);
        //System.out.println("Identity: " + authentication.getIdentity());
    }
}
