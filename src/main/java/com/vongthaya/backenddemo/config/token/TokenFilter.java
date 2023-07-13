package com.vongthaya.backenddemo.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.vongthaya.backenddemo.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// GenericFilterBean use for create custom filter
@Component
public class TokenFilter extends GenericFilterBean  {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.isBlank()) {
            // if don't have authorization (token) then let spring denied access
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!authorization.startsWith("Bearer ")) {
            // if authorization not start with "Bearer " then let spring denied access
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = authorization.split(" ")[1];

        DecodedJWT decodedJWT = tokenService.verify(token);
        if (decodedJWT == null) {
            // if token invalid then let spring denied access
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // user_id
        int principal = decodedJWT.getClaim("principal").asInt();
        String role = decodedJWT.getClaim("role").asString();
        String credentials = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        // 1.principal can be userId, username
        // 2.credentials is password
        // 3.authorities is roles

        // we use this class to hold authenticated data
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, credentials, authorities);

        // tell spring this user is authenticated now.
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

    }

}
