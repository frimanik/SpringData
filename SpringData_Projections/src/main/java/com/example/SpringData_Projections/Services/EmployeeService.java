package com.example.SpringData_Projections.Services;

import com.example.SpringData_Projections.Entities.Employee;
import com.example.SpringData_Projections.Projections.DepartmentNameProjection;
import com.example.SpringData_Projections.Projections.FullNameProjection;
import com.example.SpringData_Projections.Projections.PositionProjection;
import com.example.SpringData_Projections.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No employee with this id"));
    }


    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        if (!employeeRepository.existsById(id)) throw new IllegalArgumentException("No employee with this id");
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) throw new IllegalArgumentException("No employee with this id");
        employeeRepository.deleteById(id);
    }

    public String getFullName(Long id) {
        FullNameProjection projection = employeeRepository.findFullNameById(id).orElseThrow(()->new IllegalArgumentException("No employee with this id exists"));;
        return projection.getFullName();
    }

    public String getPosition(Long id) {
        PositionProjection projection = employeeRepository.findPositionById(id).orElseThrow(()->new IllegalArgumentException("No employee with this id exists"));
        return projection.getPosition();
    }

    public String getDepartmentName(Long id) {
        DepartmentNameProjection projection = employeeRepository.findDepartmentNameById(id).orElseThrow(()->new IllegalArgumentException("No employee with this id exists"));
        return projection.getDepartmentName();
    }
}