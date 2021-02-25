package com.dailyquest.api.config.security.auth.JWT;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtils jwtUtils;
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                String header = request.getHeader("Authorization");
                if(header != null && header.startsWith("Bearer ")){
                    UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
                    
                    if(auth != null){
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    }else{
                        response.setStatus(401);
                        response.setContentType("application/json"); 
                        response.getWriter().append(json());
                    }
                }
                chain.doFilter(request, response);
    }

    private String json() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
            + "\"status\": 401, "
            + "\"error\": \"Não autorizado\", "
            + "\"message\": \"Faça login para continuar\"}";
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        if(jwtUtils.tokenValido(token)){
            String username = jwtUtils.getUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
    
}