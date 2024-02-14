package com.example.SpringData_Projections;

import com.example.SpringData_Projections.Entities.Employee;
import com.example.SpringData_Projections.Projections.DepartmentNameProjection;
import com.example.SpringData_Projections.Projections.FullNameProjection;
import com.example.SpringData_Projections.Projections.PositionProjection;
import com.example.SpringData_Projections.Repositories.EmployeeRepository;
import com.example.SpringData_Projections.Services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployee() {

        long employeeId = 1L;
        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(expectedEmployee));

        Employee result = employeeService.getEmployeeById(employeeId);

        assertThat(result).isEqualTo(expectedEmployee);
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void addEmployee() {

        Employee employeeToAdd = new Employee();

        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeToAdd);

        Employee result = employeeService.addEmployee(employeeToAdd);

        assertThat(result).isEqualTo(employeeToAdd);
        verify(employeeRepository, times(1)).save(employeeToAdd);
    }

    @Test
    void updateEmployee() {

        long employeeId = 1L;
        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(employeeId);

        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeToUpdate);

        Employee result = employeeService.updateEmployee(employeeId, employeeToUpdate);

        assertThat(result).isEqualTo(employeeToUpdate);
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).save(employeeToUpdate);
    }

    @Test
    void updateEmployee_NonExistingId_ThrowsException() {

        long employeeId = 1L;
        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(employeeId);

        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(employeeId, employeeToUpdate));
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee() {
        long employeeId = 1L;

        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void getFullName() {
        // Arrange
        long employeeId = 1L;
        String expectedFullName = "John Doe";
        when(employeeRepository.findFullNameById(employeeId)).thenReturn(Optional.of(new FullNameProjection() {
            @Override
            public String getFullName() {
                return expectedFullName;
            }
        }));

        // Act
        String result = employeeService.getFullName(employeeId);

        // Assert
        assertThat(result).isEqualTo(expectedFullName);
        verify(employeeRepository, times(1)).findFullNameById(employeeId);
    }

    @Test
    void getPosition() {
        // Arrange
        long employeeId = 1L;
        String expectedPosition = "Software Engineer";
        when(employeeRepository.findPositionById(employeeId)).thenReturn(Optional.of(new PositionProjection() {
            @Override
            public String getPosition() {
                return expectedPosition;
            }
        }));

        // Act
        String result = employeeService.getPosition(employeeId);

        // Assert
        assertThat(result).isEqualTo(expectedPosition);
        verify(employeeRepository, times(1)).findPositionById(employeeId);
    }

    @Test
    void getDepartmentName() {
        // Arrange
        long employeeId = 1L;
        String expectedDepartmentName = "Engineering";
        when(employeeRepository.findDepartmentNameById(employeeId)).thenReturn(Optional.of(new DepartmentNameProjection() {
            @Override
            public String getDepartmentName() {
                return expectedDepartmentName;
            }
        }));

        // Act
        String result = employeeService.getDepartmentName(employeeId);

        // Assert
        assertThat(result).isEqualTo(expectedDepartmentName);
        verify(employeeRepository, times(1)).findDepartmentNameById(employeeId);
    }
}
