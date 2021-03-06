package com.weiwudev.filters;

import com.weiwudev.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter() {
        super(Config.class);
    }

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            try {
                ServerHttpRequest request = exchange.getRequest();
                String accessToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (accessToken != null && accessToken.startsWith("Bearer ")) {
                    accessToken = accessToken.substring(7);
                    Claims claims = jwtUtil.getAllClaimsFromToken(accessToken);
                    List<String> rolesMap = claims.get("roles", List.class);

                    StringBuilder roles = new StringBuilder();
                    String separator = "";
                    for (String role : rolesMap) {
                        roles.append(separator);
                        separator = ":";
                        roles.append(role);
                    }

                    ServerHttpRequest modifiedRequest = request.mutate().header("USER_NAME", claims.getSubject())
                            .header("USER_ROLES", roles.toString()).build();

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                }
            }catch (Exception e){
                return chain.filter(exchange);
            }
            return chain.filter(exchange);
        };
    }


    public static class Config {
        // Put the configuration properties
    }
}

