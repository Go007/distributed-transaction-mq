package com.hong.dispatch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wanghong
 * @date 2020/03/29 12:55
 **/
@SpringBootApplication
@MapperScan("com.hong.dispatch.mapper")
public class DispatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DispatchApplication.class, args);
    }
}
