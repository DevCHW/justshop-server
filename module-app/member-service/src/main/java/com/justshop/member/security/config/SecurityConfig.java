package com.justshop.member.security.config;

import com.justshop.member.security.CustomAuthenticationManager;
import com.justshop.member.api.auth.application.LoginService;
import com.justshop.member.security.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment env;
    private final LoginService loginService;
    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // jwt 인증 방식이기에 csrf 해제.
        http.csrf().disable();

        // cors 해제
        http.cors().disable();

        // 같은 출처에 대하여만 iframe 옵션 활성
        http.headers().frameOptions().sameOrigin();

//        http.authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/actuator/**").permitAll()
//                .and()
        http.addFilter(getAuthenticationFilter());

        // session 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(env, loginService);
        authenticationFilter.setAuthenticationManager(customAuthenticationManager);

        return authenticationFilter;
    }

}
