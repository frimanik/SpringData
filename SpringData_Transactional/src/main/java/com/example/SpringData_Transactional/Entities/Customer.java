package com.example.SpringData_Transactional.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String username;
    long balance;

    public Customer(long id, String username, long balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Customer() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
