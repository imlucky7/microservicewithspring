/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.mc.order.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nagasrinivas
 */
public class Order implements Serializable {

    static final Map<Long, Order> ORDERS = new HashMap<>();

    static {
        ORDERS.put(1L, new Order(1L, 120));
        ORDERS.put(2L, new Order(2L, 34));
        ORDERS.put(3L, new Order(1L, 3534));
        ORDERS.put(4L, new Order(3L, 345));
        ORDERS.put(5L, new Order(5L, 234));
        ORDERS.put(6L, new Order(3L, 643));
        ORDERS.put(7L, new Order(5L, 342));
        ORDERS.put(8L, new Order(4L, 646));
        ORDERS.put(9L, new Order(7L, 343));
        ORDERS.put(10L, new Order(4L, 665));
        ORDERS.put(11L, new Order(4L, 342));
        ORDERS.put(12L, new Order(2L, 784));
        ORDERS.put(13L, new Order(7L, 534));
        ORDERS.put(14L, new Order(8L, 645));
        ORDERS.put(15L, new Order(1L, 456));
    }

    private long id;
    private Date orderedDate = new Date();
    private Long customerId;
    private float totalPrice;

    public Order() {
    }

    public Order(Long customerId, float totalPrice) {
        this.customerId = customerId;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public static List<Order> findAll() {
        return new ArrayList(ORDERS.values());
    }

    public static Order findOne(long id) {
        return ORDERS.get(id);
    }

    public static List<Order> findByCustId(long custId) {
        List<Order> orders = new ArrayList<>();
        for (Order order : ORDERS.values()) {
            if (order.customerId == custId) {
                orders.add(order);
            }
        }
        return orders;
    }

    public static Map findOrderSummaryByCustId(long custId) {
        int cnt = 0;
        float total = 0;
        for (Order order : ORDERS.values()) {
            if (order.customerId == custId) {
                cnt++;
                total += order.getTotalPrice();
            }
        }
        Map map = new HashMap();
        map.put("totalOrders", cnt);
        map.put("totalSpent", total);
        map.put("averageSpent", total / cnt);
        return map;
    }

}
