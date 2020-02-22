package ru.fds.tavrzcms_tl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TavrzcmsTlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TavrzcmsTlApplication.class, args);
    }

}
