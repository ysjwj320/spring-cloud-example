package com.ysj.spring.cloud.example.gateway.security;

import com.ysj.spring.cloud.example.gateway.security.converter.BearerTokenAuthenticationConverter;
import com.ysj.spring.cloud.example.gateway.security.handler.CustomAccessDeniedHandler;
import com.ysj.spring.cloud.example.gateway.security.handler.CustomServerAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.MediaTypeServerWebExchangeMatcher;

import java.util.Collections;

@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private static final String AUTHENTICATION_PREFIX = "/spring/cloud/example/auth/v1/";

    @Autowired
    private TokenAuthenticationManager authenticationManager;

    @Autowired
    private BearerTokenAuthenticationConverter authenticationConverter;

    @Autowired
    private CustomServerAuthenticationEntryPoint entryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationWebFilter authenticationWebFilter;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        MediaTypeServerWebExchangeMatcher restMatcher = new MediaTypeServerWebExchangeMatcher(
                MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_XML,
                MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_XML);
        restMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        return http.csrf().disable()
                // Demonstrate that method security works
                // Best practice to use both for defense in depth
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).denyAll()
                        .pathMatchers(HttpMethod.TRACE).denyAll()
                        .pathMatchers(AUTHENTICATION_PREFIX + "/api/signUp")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(authenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public AuthenticationWebFilter authenticationFilter() {
        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(entryPoint));
        authenticationFilter.setServerAuthenticationConverter(authenticationConverter);
        authenticationFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return authenticationFilter;
    }

    @EventListener
    public void logFilters(ApplicationReadyEvent event) {

    }
}
