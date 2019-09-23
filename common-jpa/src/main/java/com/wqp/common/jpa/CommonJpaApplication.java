package com.wqp.common.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CommonJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonJpaApplication.class, args);
    }

}
