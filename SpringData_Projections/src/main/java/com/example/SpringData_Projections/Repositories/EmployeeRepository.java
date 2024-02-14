package com.example.SpringData_Projections.Repositories;

import com.example.SpringData_Projections.Entities.Employee;
import com.example.SpringData_Projections.Projections.DepartmentNameProjection;
import com.example.SpringData_Projections.Projections.FullNameProjection;
import com.example.SpringData_Projections.Projections.PositionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT CONCAT(e.firstName, ' ', e.lastName) AS fullName FROM Employee e WHERE e.id = :id")
    Optional<FullNameProjection> findFullNameById(Long id);

    @Query("SELECT e.position AS position FROM Employee e WHERE e.id=id")
    Optional<PositionProjection> findPositionById(Long id);

    @Query("SELECT e.department.name AS departmentName FROM Employee e WHERE e.id=id")
    Optional<DepartmentNameProjection> findDepartmentNameById(Long id);
}
