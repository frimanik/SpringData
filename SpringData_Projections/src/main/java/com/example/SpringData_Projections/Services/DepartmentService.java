package com.example.SpringData_Projections.Services;

import com.example.SpringData_Projections.Entities.Department;
import com.example.SpringData_Projections.Repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository deparmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository deparmentRepository) {
        this.deparmentRepository = deparmentRepository;
    }

    public Department addDepartment(Department department) {
        return deparmentRepository.save(department);
    }
}
