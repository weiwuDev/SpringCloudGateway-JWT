package com.weiwudev.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomFilterTwo extends
        AbstractGatewayFilterFactory<CustomFilterTwo.Config> {

    public CustomFilterTwo() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Pre-processing
            return chain.filter(exchange)
                    //Post-processing
                    .then(Mono.fromRunnable(() -> {
                        /*
                        List<String> setCookieHeader = exchange.getResponse().getHeaders().get("Set-Cookie");
                        if(setCookieHeader != null) {
                            String temp = setCookieHeader.get(0);
                            temp = temp.replace("Lax", "None; Secure");
                            exchange.getResponse().getHeaders().set("Set-Cookie", temp);
                        }
                         */
                        exchange.getResponse().getHeaders().remove("WWW-Authenticate");
                    }));
        };
    }
    public static class Config {
    }
}