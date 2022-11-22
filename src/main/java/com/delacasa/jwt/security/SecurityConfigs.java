package com.delacasa.jwt.security;

import com.delacasa.jwt.jwt.JwtUtil;
import com.delacasa.jwt.security.auth.AuthProvider;
import com.delacasa.jwt.security.auth.UsernameAndPasswordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigs {

    private final AuthProvider authProvider;
    private final JwtUtil jwtUtil;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/sign-up/**", "/login/**")
                .permitAll()
                .and()
                .formLogin()
                .and()
                .authenticationManager(authManager())
                .addFilter(new UsernameAndPasswordFilter(authManager(), jwtUtil));


        return http.build();
    }

    //Authorise quel que soit la config précèdente.
    @Bean
    WebSecurityCustomizer webSecurityCustomizerDev() {
        return web -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    ProviderManager authManager() {
        return new ProviderManager(authProvider);
    }


}
