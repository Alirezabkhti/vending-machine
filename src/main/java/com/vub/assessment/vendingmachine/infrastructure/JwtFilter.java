package com.vub.assessment.vendingmachine.infrastructure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vub.assessment.vendingmachine.application.user.CustomUserDetailsService;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    private String username = null;
    private String jwt = null;

	public JwtFilter(CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeaderIsNotExist(authorizationHeader)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            extractJWTAndUsernameFromAuthorizationHeader(authorizationHeader);
            checkUserExistenceAndAuthenticate(httpServletRequest);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean authenticationHeaderIsNotExist(
            String authorizationHeader
    ) {
        return authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ");
    }

    private void extractJWTAndUsernameFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
    }

    private void checkUserExistenceAndAuthenticate(HttpServletRequest httpServletRequest) {
        if (isUsernameExistAndSecurityContextIsNull()) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                authenticateUser(httpServletRequest, userDetails);
            }
        }
    }

    private void authenticateUser(HttpServletRequest httpServletRequest, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private boolean isUsernameExistAndSecurityContextIsNull() {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
