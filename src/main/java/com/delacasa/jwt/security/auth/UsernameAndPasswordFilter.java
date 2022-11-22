package com.delacasa.jwt.security.auth;

import com.delacasa.jwt.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        final String principal = request.getParameter("username");
        final String credentials = request.getParameter("password");


        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, credentials);

        return authenticationManager.authenticate(auth); // appelera la méthode authenticate de AuthProvider
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        response.setHeader("Authorization", "Bearer " + jwtUtil.createToken(authResult));

    }

}
