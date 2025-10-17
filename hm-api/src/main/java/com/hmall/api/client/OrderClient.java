package com.hmall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("trade-service")
public interface OrderClient {
    @PutMapping("/{orderId}")
    public void updateById(@PathVariable("orderId") Long orderId);
}
