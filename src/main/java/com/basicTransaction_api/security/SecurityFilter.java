package com.basicTransaction_api.security;

import com.basicTransaction_api.domain.exceptions.ValidationExceptions;
import com.basicTransaction_api.repository.UserRepository;
import com.basicTransaction_api.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt = recoverToken(request);

        if (tokenJwt != null) {
            var subject = tokenHandler.verifyToken(tokenJwt);
            var user = userRepository.findByEmail(subject)
                    .orElseThrow(() -> new ValidationExceptions("Subject não encontrado"));

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); //forçando a authentication
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorizationToken = request.getHeader("Authorization");

        if (authorizationToken != null) {
            return authorizationToken.replace("Bearer ", "");
        }

        return null;
    }
}
