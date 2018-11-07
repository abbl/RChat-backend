package pl.abbl.reactchat.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static pl.abbl.reactchat.definitions.SecurityConstants.HEADER_STRING;
import static pl.abbl.reactchat.definitions.SecurityConstants.SECRET;
import static pl.abbl.reactchat.definitions.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        String cookieHeader = checkIfCookieContainsAuthorizationHeader(request.getCookies());

        if(cookieHeader != null){
            header = cookieHeader;
        }

        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = detachTokenFromRequestHeader(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private String checkIfCookieContainsAuthorizationHeader(Cookie[] cookies){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(HEADER_STRING.equals(cookie.getName())){
                    System.out.println("C:" + cookie.getValue());
                    return cookie.getValue().replaceFirst("%20", " ");
                }
            }
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken detachTokenFromRequestHeader(String token) {
        System.out.println("Token:" + token);
        if(token != null){
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if(user != null){
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
