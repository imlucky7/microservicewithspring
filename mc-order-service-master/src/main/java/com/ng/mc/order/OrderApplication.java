/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.mc.order;

import com.ng.mc.order.db.Order;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
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
@SpringBootApplication
@EnableDiscoveryClient
public class OrderApplication {

    public static final String LOGGING_SERVICE_NAME = "zipkin-service";

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OrderApplication.class, args);
    }

}

@RestController
class OrderController {

    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

    public static final String CUSTOMER_SERVICE_URL = "http://CUSTOMER-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/info")
    public ResponseEntity info() {
        LOG.log(Level.INFO, "laoding info");
        return new ResponseEntity("Response from Order Service", HttpStatus.OK);
    }

    @RequestMapping("/")
    public ResponseEntity findAll() {
        LOG.log(Level.INFO, "loading Orders");
        List<Order> orders = Order.findAll();
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @RequestMapping("/{orderId}")
    public ResponseEntity getOrder(@PathVariable("orderId") long orderId) {
        LOG.log(Level.INFO, "loading Order with id {0}", orderId);
        Order order = Order.findOne(orderId);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @RequestMapping("/by-customers/{customerId}")
    public ResponseEntity getCustomerOrders(@PathVariable("customerId") long customerId) {
        LOG.log(Level.INFO, "loading Orders for customer with id {0}",customerId);
        List<Order> orders = Order.findByCustId(customerId);
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @RequestMapping("/by-customers/{customerId}/orders-summary")
    public ResponseEntity getOrdersSummary(@PathVariable("customerId") long customerId) {
        LOG.log(Level.INFO, "loading Orders summary for customer with id {0}",customerId);
        Map summary = Order.findOrderSummaryByCustId(customerId);
        return new ResponseEntity(summary, HttpStatus.OK);
    }
}
