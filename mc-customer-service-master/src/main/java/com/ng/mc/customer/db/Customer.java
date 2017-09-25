/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.mc.customer.db;

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
public class Customer
        implements Serializable {

    private static final Map<Long, Customer> CUSTOMERS = new HashMap<>();

    static {
        CUSTOMERS.put(1L, new Customer("John", "Doe"));
        CUSTOMERS.put(2L, new Customer("Naga", "Srinivas"));
        CUSTOMERS.put(3L, new Customer("Durga", "Prasad"));
        CUSTOMERS.put(4L, new Customer("ABC", "XYZ"));
        CUSTOMERS.put(5L, new Customer("Kiran", "Kumar"));
        CUSTOMERS.put(6L, new Customer("David", "K"));
    }

    private long id;
    private String firstName;
    private String lastName;
    private Date registeredDate = new Date();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public static List<Customer> findAll() {
        return new ArrayList(CUSTOMERS.values());
    }

    public static Customer findOne(long id) {
        return CUSTOMERS.get(id);
    }

}
