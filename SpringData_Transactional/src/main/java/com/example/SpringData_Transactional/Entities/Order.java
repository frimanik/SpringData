package com.example.SpringData_Transactional.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="custom_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    Customer customer;
    @OneToMany
    List<Product> products;
    long totalAmount;

    public Order(long id, Customer customer, List<Product> products, long totalAmount) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
