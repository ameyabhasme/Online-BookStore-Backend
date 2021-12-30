package com.bridgelabz.bookstore.config;

import com.bridgelabz.bookstore.service.UserSecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    public String jwtToken = null;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String email = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            try {
                jwtToken = requestTokenHeader.substring(7);
                email = this.jwtUtils.extractUsername(jwtToken);

            }catch (ExpiredJwtException expiredJwtException){
                expiredJwtException.printStackTrace();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        if ( email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userSecurityService.loadUserByUsername(email);
            if(jwtUtils.validateToken(jwtToken , userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}