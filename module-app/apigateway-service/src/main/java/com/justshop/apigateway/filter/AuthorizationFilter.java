package com.justshop.apigateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private Environment env;

    public AuthorizationFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    // login -> token -> users (with token) -> header(include token)
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders()
                    .get(HttpHeaders.AUTHORIZATION).get(0);

            String jwt = authorizationHeader.replace("Bearer", "");
            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            log.info("회원 정보={}", getInfo(jwt));
            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        byte[] tokenSecret = env.getProperty("token.secret").getBytes();
        String encodedSecret = Base64.getEncoder().encodeToString(tokenSecret);

        String subject = null;
        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(encodedSecret)
                    .build()
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception e) {
            returnValue = false;
        }

        if (!StringUtils.hasText(subject)) {
            returnValue = false;
        }

        return returnValue;
    }

    private String getInfo(String jwt) {
        byte[] tokenSecret = env.getProperty("token.secret").getBytes();
        String encodedSecret = Base64.getEncoder().encodeToString(tokenSecret);
        return Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(jwt).getBody().getSubject();
    }

    // Mono, Flux -> Spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.info(error);

        return response.setComplete();
    }

    public static class Config {

    }

}
