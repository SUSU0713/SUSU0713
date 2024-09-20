package com.mashibing.servicePrice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.mashibing.servicePrice.mapper")
public class ServicePriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePriceApplication.class);
    }
}
