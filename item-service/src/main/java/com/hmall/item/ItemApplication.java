package com.hmall.item;

import com.hmall.api.config.OpenFeignIntercepter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.hmall.item.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hmall.api.client",defaultConfiguration = OpenFeignIntercepter.class)
public class ItemApplication {
    public static void main(String[] args) {

        SpringApplication.run(ItemApplication.class, args);
    }
}