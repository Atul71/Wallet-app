package com.wallet.wallet_app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;


    public AuthFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if(path.equals("/health") || path.equals("/auth/login") || path.equals("/users")){
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")){
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return;
        }
        String token = header.substring(7);
        String userId = redisTemplate.opsForValue().get("auth:token:" + token);
        if(userId==null){
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
