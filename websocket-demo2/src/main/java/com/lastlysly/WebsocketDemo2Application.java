package com.lastlysly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebsocketDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketDemo2Application.class, args);
    }

}
