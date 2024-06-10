package com.example.demo.filter;

import com.example.demo.bean.dto.UserDetailsDto;
import com.example.demo.constants.CommonConstants;
import com.example.demo.service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

// This class helps us to validate the generated jwt token
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        logger.info("Has auth token");
        String token = getAccessToken(request);

        if (!jwtUtil.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        logger.info("Has valid accessToken");

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        logger.info("Setting auth context");
        UserDetails userDetails = getUserDetails(token);
        logger.info("Got userDetails:{}", userDetails.getUsername() + " : " + userDetails.getAuthorities() + " : " + userDetails.getPassword());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        String userName = jwtUtil.extractUsernameFromToken(token);
        UserDetailsDto userDetails = new UserDetailsDto();
        userDetails.setUserName(userName);
        Claims claims = jwtUtil.extractAllClaimsFromToken(token);
        List<String> roles = (List<String>) claims.get(CommonConstants.ROLES);
        Collection<? extends GrantedAuthority> existingAuthorities = userDetails.getAuthorities();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(existingAuthorities);
        for(String role: roles){
            updatedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        userDetails.setAuthorities(updatedAuthorities);
        userDetails.setRoles(new HashSet<>(roles));
        logger.info("Authorities: {}, Roles: {}", userDetails.getAuthorities(), userDetails.getRoles());
        return userDetails;
    }

}
