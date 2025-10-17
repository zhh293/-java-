package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class OpenFeignIntercepter {
    @Bean
    public RequestInterceptor userInfoIntercepter() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long user = UserContext.getUser();
                requestTemplate.header("user-info",user.toString());
            }

        };
    }
}
