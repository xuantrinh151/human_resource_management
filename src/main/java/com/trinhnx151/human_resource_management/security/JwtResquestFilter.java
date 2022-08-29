package com.trinhnx151.human_resource_management.security;

import com.trinhnx151.human_resource_management.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtResquestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null;
        TokenPayload tokenPayload = null;
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Token ")) {
//            token = requestTokenHeader.substring(6).trim();
//            tokenPayload = JwtTokenUtil.getTokenPayLoad(token);
//        } else {
//            System.out.println("jwt dont not start with 'Token' ");
//
//        }
        filterChain.doFilter(request, response);
    }
}
