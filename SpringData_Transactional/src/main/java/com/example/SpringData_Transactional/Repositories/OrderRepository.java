package com.example.SpringData_Transactional.Repositories;

import com.example.SpringData_Transactional.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
