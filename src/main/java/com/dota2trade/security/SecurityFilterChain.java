package com.dota2trade.security;

import com.dota2trade.dao.UserDao;
import com.dota2trade.model.User;
import jcifs.ntlmssp.Type3Message;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: ljj
 * Date: 12-9-20
 * Time: 下午11:35
 */
public class SecurityFilterChain extends OncePerRequestFilter implements Filter {

    private UserDao userDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter work!");
        SAuthentication au = (SAuthentication) request.getSession().getAttribute("sauthentication");
        String requestUrl = request.getRequestURI();
        System.out.println(requestUrl);
        if (requestUrl.equals(DomainSecurity.dologinUrl)){
            //进行登录动作
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            String referer = getReferer(request.getHeader("Referer"));
            if (referer == null){
                referer = "/";
            }
            User user = userDao.getUser(account,password);
            if (user == null){
                //login fail
                response.sendRedirect(DomainSecurity.permissionRedirect+"?referer="+referer);
            }else {
                //login success
                SAuthentication newau = new SAuthentication();
                newau.setAccount(user.getAccount());
                request.getSession().setAttribute("sauthentication",newau);
                if (user.getAccount().equals("admin")){
                    response.sendRedirect("/admin.html");
                }else{
                    response.sendRedirect(referer);
                }
            }
            return ;
        }
        if (au == null && !DomainSecurity.contains(DomainSecurity.whitelist,requestUrl)){
            //not login access domain not in whitlist redirect
            response.sendRedirect(DomainSecurity.permissionRedirect+"?referer="+requestUrl);
            return;
        }else{
            //if have access priority
        }
        /*String auth = request.getHeader("Authorization");
        if (auth == null){
            response.setHeader("WWW-Authenticate", "NTLM");
            response.setStatus(response.SC_UNAUTHORIZED);
            response.setContentLength(0) ;
            response.flushBuffer();
            return;
        }

        if (auth.startsWith("NTLM ")){
            byte[] msg = new sun.misc.BASE64Decoder().decodeBuffer(auth.substring(5));
            int off = 0, length, offset;
            if (msg[8] == 1){
                byte z = 0;
                byte[] msg1 = {(byte)'N', (byte)'T', (byte)'L', (byte)'M', (byte)'S', (byte)'S', (byte)'P', z,(byte)2, z, z, z, z, z, z, z,(byte)40, z, z, z, (byte)1, (byte)130, z, z,z, (byte)2, (byte)2, (byte)2, z, z, z, z, z, z, z, z, z, z, z, z};
                response.setHeader("WWW-Authenticate", "NTLM " + new sun.misc.BASE64Encoder().encodeBuffer(msg1));
                response.setStatus(response.SC_UNAUTHORIZED);
                response.setContentLength(0) ;
                response.flushBuffer();
                return;
            }else if (msg[8] == 3){
                //Did Authentication Succeed? All this is always printed.

                Type3Message type3 = new Type3Message(msg);

                System.out.println("osUser: " + type3.getUser());
                System.out.println("osRemoteHost: + " + type3.getWorkstation());
                System.out.println("osDomain: " + type3.getDomain());

            }
        }*/
        chain.doFilter(request,  response);
    }

    public static String getReferer(String url)
    {
        int a = url.indexOf("referer=");
        if (a == -1){
            return  null;
        }else{
            return url.substring(a + 8);
        }
    }

    //===========setter and getter method

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
