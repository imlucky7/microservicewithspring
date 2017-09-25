/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.mc.customer;

import com.ng.mc.customer.db.Customer;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nagasrinivas
 */
@EnableEurekaClient
@SpringBootApplication
public class CustomerApplication {

    public static final String ORDER_SERVICE_URL = "http://ORDER-SERVICE";

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CustomerApplication.class, args);
    }

}

@RestController
class CustomerController {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());

    @RequestMapping("/info")
    public ResponseEntity info() {
        LOG.log(Level.INFO, "laoding info");
        return new ResponseEntity("Response from Customer Service", HttpStatus.OK);
    }

    @RequestMapping("/")
    public ResponseEntity findAll() {
        LOG.log(Level.INFO, "loading all customers");
        List<Customer> customers = Customer.findAll();
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    @RequestMapping("/{custId}")
    public ResponseEntity findOne(@PathVariable("custId") long custId) {
        LOG.log(Level.INFO, "loading custoer with id {0}", custId);
        Customer customer = Customer.findOne(custId);
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @RequestMapping("/{custId}/orders")
    public ResponseEntity findOrdersByCustomer(@PathVariable("custId") long custId) {
        LOG.log(Level.INFO, "loading orders for custoer with id {0}", custId);
        Object orders = restTemplate.getForObject(CustomerApplication.ORDER_SERVICE_URL + "/by-customers/" + custId, Object.class);
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @RequestMapping("/{custId}/orders/summary")
    public ResponseEntity findOrdersSummaryByCustomer(@PathVariable("custId") long custId) {
        LOG.log(Level.INFO, "loading orders summary for custoer with id {0}", custId);
        Map summary = restTemplate.getForObject(CustomerApplication.ORDER_SERVICE_URL + "/by-customers/" + custId + "/orders-summary", Map.class);
        return new ResponseEntity(summary, HttpStatus.OK);
    }

}
