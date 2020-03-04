package com.lastlysly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 14:26
 **/
@RestController
@RequestMapping("/test")
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
