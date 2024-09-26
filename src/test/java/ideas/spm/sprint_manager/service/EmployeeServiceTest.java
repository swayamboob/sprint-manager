package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;  // Mock the EmployeeRepository

    @InjectMocks
    EmployeeService employeeService;  // Inject mocks into the EmployeeService

    @Test
    void getEmployee() {
        employeeService.getEmployee();
        verify(employeeRepository, times(1)).findBy();  // Verify that findBy was called once
    }

    @Test
    void saveEmployee_whenEmployeeDoesNotExist() {
        Employee employee = new Employee();
        employee.setEmployeeID(1);

        when(employeeRepository.existsById(employee.getEmployeeID())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.saveEmployee(employee);

        assertNotNull(result);  // Assert that the saved employee is not null
        verify(employeeRepository, times(1)).save(employee);  // Verify save was called
    }

    @Test
    void saveEmployee_whenEmployeeExists() {
        Employee employee = new Employee();
        employee.setEmployeeID(1);

        when(employeeRepository.existsById(employee.getEmployeeID())).thenReturn(true);

        Employee result = employeeService.saveEmployee(employee);

        assertNull(result);  // Assert that the result is null
        verify(employeeRepository, times(0)).save(employee);  // Verify save was not called
    }

    @Test
    void updateEmployee_whenEmployeeExists() {
        Employee employee = new Employee();
        employee.setEmployeeID(1);

        when(employeeRepository.existsById(employee.getEmployeeID())).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.updateEmployee(employee);

        assertNotNull(result);  // Assert that the updated employee is not null
        verify(employeeRepository, times(1)).save(employee);  // Verify save was called
    }

    @Test
    void updateEmployee_whenEmployeeDoesNotExist() {
        Employee employee = new Employee();
        employee.setEmployeeID(1);

        when(employeeRepository.existsById(employee.getEmployeeID())).thenReturn(false);

        Employee result = employeeService.updateEmployee(employee);

        assertNull(result);  // Assert that the result is null
        verify(employeeRepository, times(0)).save(employee);  // Verify save was not called
    }

    @Test
    void getEmployeeById() {
        int employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeID(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(employee);

        Employee result = employeeService.getEmployeeById(employeeId);

        assertEquals(employee, result);  // Assert that the retrieved employee is as expected
        verify(employeeRepository, times(1)).findById(employeeId);  // Verify that findById was called
    }

    @Test
    void deleteEmployee() {
        int employeeId = 1;
        when(employeeRepository.deleteByEmployeeID(employeeId)).thenReturn(1);

        Integer result = employeeService.deleteEmployee(employeeId);

        assertEquals(1, result);  // Assert that the deletion result is as expected
        verify(employeeRepository, times(1)).deleteByEmployeeID(employeeId);  // Verify that deleteByEmployeeID was called
    }

    @Test
    void findEmployee_whenExists() {
        Employee employee = new Employee();
        employee.setEmployeeID(1);

        when(employeeRepository.existsById(employee.getEmployeeID())).thenReturn(true);

        boolean exists = employeeService.findEmployee(employee);

        assertTrue(exists);  // Assert that the employee exists
        verify(employeeRepository, times(1)).existsById(employee.getEmployeeID());  // Verify that existsById was called
    }

    @Test
    void findEmployee_whenDoesNotExist() {
        Employee employee = new Employee();
        employee.setEmployeeID(1);

        when(employeeRepository.existsById(employee.getEmployeeID())).thenReturn(false);

        boolean exists = employeeService.findEmployee(employee);

        assertFalse(exists);  // Assert that the employee does not exist
        verify(employeeRepository, times(1)).existsById(employee.getEmployeeID());  // Verify that existsById was called
    }

    // Additional test cases can be added for methods like findByEmployeeEmail and loadUserByUsername
}
