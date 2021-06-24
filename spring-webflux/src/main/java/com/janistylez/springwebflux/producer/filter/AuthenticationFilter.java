package com.janistylez.springwebflux.producer.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        if(!serverWebExchange.getRequest().getHeaders().containsKey("AUTH")) {
            serverWebExchange.getResponse().getHeaders().add("AUTH", "ANONYMOUS");
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
