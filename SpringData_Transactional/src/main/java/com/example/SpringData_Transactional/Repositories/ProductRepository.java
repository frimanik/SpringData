package com.example.SpringData_Transactional.Repositories;

import com.example.SpringData_Transactional.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
