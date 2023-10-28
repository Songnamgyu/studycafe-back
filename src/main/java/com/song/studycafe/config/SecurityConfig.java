package com.song.studycafe.config;

import com.song.studycafe.exception.JwtAccessDeniedHandler;
import com.song.studycafe.exception.JwtAuthenticationEntryPoint;
import com.song.studycafe.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Component
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    //Password는 BCrpytPasswordEncoder 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//       return (web) -> web.ignoring().requestMatchers(
//                new AntPathRequestMatcher("/h2-console/**"),
//                new AntPathRequestMatcher("/favicon/ico"),
//                new AntPathRequestMatcher("/auth/**"),
//                new AntPathRequestMatcher("/**"));
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .headers((headers) -> headers.frameOptions().sameOrigin())
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers(
                       new AntPathRequestMatcher("/auth/**"),
                       new AntPathRequestMatcher("/h2-console/**"),
                       new AntPathRequestMatcher("/favicon.ico"),
                       new AntPathRequestMatcher("/")
                ).permitAll()
                ).authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();

    }
}
