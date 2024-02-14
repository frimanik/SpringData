package com.example.SpringData_Projections.Repositories;

import com.example.SpringData_Projections.Entities.Department;
import com.example.SpringData_Projections.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
