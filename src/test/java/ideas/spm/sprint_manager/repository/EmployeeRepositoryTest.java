package ideas.spm.sprint_manager.repository;

import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;  // Mocking the repository

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes mocks

        // Setup a sample employee
        employee = new Employee();
        employeeDTO= mock(EmployeeDTO.class);
        employee.setEmployeeID(1);
        employee.setEmployeeName("John Doe");
        employee.setEmployeeEmail("john.doe@example.com");
    }

    @Test
    void testFindById() {
        // Mocking the repository method call
        when(employeeRepository.findById(1)).thenReturn(employee);

        // Call the repository method
        Employee foundEmployee = employeeRepository.findById(1);

        // Verify the interaction and assertions
        assertNotNull(foundEmployee);
        assertEquals(1, foundEmployee.getEmployeeID());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testFindByEmployeeEmail() {
        // Mocking the repository method call
        when(employeeRepository.findByEmployeeEmail("john.doe@example.com")).thenReturn(employee);

        // Call the repository method
        Employee foundEmployee = employeeRepository.findByEmployeeEmail("john.doe@example.com");

        // Verify the interaction and assertions
        assertNotNull(foundEmployee);
        assertEquals("john.doe@example.com", foundEmployee.getEmployeeEmail());
        verify(employeeRepository, times(1)).findByEmployeeEmail("john.doe@example.com");
    }

    @Test
    void testFindByTeam() {
        // Mocking the repository method call
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(employeeRepository.findByTeam(any())).thenReturn(employees);

        // Call the repository method
        List<Employee> foundEmployees = employeeRepository.findByTeam(new Team());

        // Verify the interaction and assertions
        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        verify(employeeRepository, times(1)).findByTeam(any());
    }

    @Test
    void testDeleteByEmployeeID() {
        // Mocking the repository method call
        when(employeeRepository.deleteByEmployeeID(1)).thenReturn(1);

        // Call the repository method
        Integer result = employeeRepository.deleteByEmployeeID(1);

        // Verify the interaction and assertions
        assertEquals(1, result);
        verify(employeeRepository, times(1)).deleteByEmployeeID(1);
    }

    @Test
    void testFindByEmployeeIDAndEmployeeEmail() {
        // Mocking the repository method call
        when(employeeRepository.findByEmployeeIDAndEmployeeEmail(1, "john.doe@example.com")).thenReturn(employeeDTO);

        // Call the repository method
        EmployeeDTO foundEmployee = employeeRepository.findByEmployeeIDAndEmployeeEmail(1, "john.doe@example.com");

        // Verify the interaction and assertions
        assertNotNull(foundEmployee);
        assertEquals(1, foundEmployee.getEmployeeID());
        verify(employeeRepository, times(1)).findByEmployeeIDAndEmployeeEmail(1, "john.doe@example.com");
    }

    @Test
    void testFindByEmployeeName() {
        // Mocking the repository method call
        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(employeeDTO);
        when(employeeRepository.findByEmployeeName("John Doe")).thenReturn(employees);

        // Call the repository method
        List<EmployeeDTO> foundEmployees = employeeRepository.findByEmployeeName("John Doe");

        // Verify the interaction and assertions
        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        assertEquals("John Doe", foundEmployees.get(0).getEmployeeName());
        verify(employeeRepository, times(1)).findByEmployeeName("John Doe");
    }

    @Test
    void testFindByEmployeeRole() {
        // Mocking the repository method call
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(employeeRepository.findByEmployeeRole("Developer")).thenReturn(employees);

        // Call the repository method
        List<Employee> foundEmployees = employeeRepository.findByEmployeeRole("Developer");

        // Verify the interaction and assertions
        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        verify(employeeRepository, times(1)).findByEmployeeRole("Developer");
    }

    @Test
    void testFindByTeamAndEmployeeRoleNot() {
        // Mocking the repository method call
        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(employeeDTO);
        when(employeeRepository.findByTeamAndEmployeeRoleNot(any(), eq("Manager"))).thenReturn(employees);

        // Call the repository method
        List<EmployeeDTO> foundEmployees = employeeRepository.findByTeamAndEmployeeRoleNot(new Team(), "Manager");

        // Verify the interaction and assertions
        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        verify(employeeRepository, times(1)).findByTeamAndEmployeeRoleNot(any(), eq("Manager"));
    }
}
