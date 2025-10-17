package com.hmall.geteway.filter;

import com.hmall.geteway.config.AuthProperties;
import com.hmall.geteway.util.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthGlobaleFilter implements GlobalFilter, Ordered {
    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取路径查看是否需要放行
        RequestPath path = request.getPath();

        if (checkPath(path.toString())) {
            return chain.filter(exchange);
        }

        //获取token
        String token=null;
        List<String> authorization = request.getHeaders().get("authorization");
        if(authorization != null && !authorization.isEmpty()){
            token=authorization.get(0);
        }
        Long userId = null;
        try {
           userId = jwtTool.parseToken(token);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String string = userId.toString();
        ServerWebExchange swe = exchange.mutate().request(
                builder -> builder.header("user-info", string)
        ).build();
        return chain.filter(swe);
    }

    @Override
    public int getOrder() {
        return 0;
    }
    public boolean checkPath(String path){
        for (String excludePath : authProperties.getExcludePaths()) {

            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }
}
