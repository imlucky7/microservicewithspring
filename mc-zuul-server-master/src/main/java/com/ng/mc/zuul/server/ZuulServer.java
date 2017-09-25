/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.mc.zuul.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nagasrinivas
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulServer {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServer.class, args);
    }

    @Bean
    public SimplePreFilter getPreFilter() {
        return new SimplePreFilter();
    }
}

@RestController
@RequestMapping(path = "/", produces = {MediaType.TEXT_HTML_VALUE})
class HomeController {

    @RequestMapping("")
    public String home() {
        return "<h2>API Gateway</h2>"
                + "<ul>"
                + "<li><a href='/customers'>All Customers</a>"
                + "<li><a href='/orders'>All Orders</a>"
                + "</u>";
    }
}
