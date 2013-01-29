package com.dota2trade.security;

import com.dota2trade.dao.UserDao;
import jcifs.ntlmssp.Type3Message;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String auth = request.getHeader("Authorization");
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
        }
        chain.doFilter(request,  response);
    }

    //===========setter and getter method

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
