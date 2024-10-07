package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.exceptions.EmployeeExceptions.EmployeeNotFoundException;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    @Test
    void getEmployeeByIdAndEmail_whenEmployeeExists() {
        int employeeId = 1;
        String email = "test@example.com";
        EmployeeDTO employeeDTO = mock(EmployeeDTO.class);
        Employee employee = new Employee();
        employee.setEmployeeID(employeeId);
        employee.setEmployeeEmail(email);

        when(employeeRepository.findByEmployeeIDAndEmployeeEmail(employeeId, email)).thenReturn(Optional.of(employeeDTO));

        EmployeeDTO result = employeeService.getEmployeeByIdAndEmail(employeeId, email);

        assertEquals(employeeDTO, result);  // Assert that the retrieved employee DTO is as expected
        verify(employeeRepository, times(1)).findByEmployeeIDAndEmployeeEmail(employeeId, email);  // Verify that the method was called
    }

    @Test
    void getEmployeeByIdAndEmail_whenEmployeeDoesNotExist() {
        int employeeId = 1;
        String email = "test@example.com";

        when(employeeRepository.findByEmployeeIDAndEmployeeEmail(employeeId, email)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeByIdAndEmail(employeeId, email));  // Assert that the exception is thrown
    }

    @Test
    void getManagerId_whenEmployeeHasTeam() {
        int employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeID(employeeId);
        Team team = new Team();
        Employee manager = new Employee();
        manager.setEmployeeID(2);
        team.setTeamManager(manager);
        employee.setTeam(team);

        when(employeeRepository.findById(employeeId)).thenReturn(employee);

        int managerId = employeeService.getManagerId(employeeId);

        assertEquals(2, managerId);  // Assert that the correct manager ID is returned
    }

    @Test
    void getManagerId_whenEmployeeHasNoTeam() {
        int employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeID(employeeId);
        employee.setTeam(null);  // No team associated

        when(employeeRepository.findById(employeeId)).thenReturn(employee);

        int managerId = employeeService.getManagerId(employeeId);

        assertEquals(-1, managerId);  // Assert that -1 is returned when there is no team
    }

    @Test
    void findByEmployeeEmail_whenEmployeeExists() {
        String email = "test@example.com";
        Employee employee = new Employee();
        employee.setEmployeeEmail(email);

        when(employeeRepository.findByEmployeeEmail(email)).thenReturn(employee);

        Employee result = employeeService.findByEmployeeEmail(email);

        assertEquals(employee, result);  // Assert that the retrieved employee is as expected
        verify(employeeRepository, times(1)).findByEmployeeEmail(email);  // Verify that the method was called
    }

    @Test
    void findByEmployeeEmail_whenEmployeeDoesNotExist() {
        String email = "test@example.com";

        when(employeeRepository.findByEmployeeEmail(email)).thenReturn(null);

        Employee result = employeeService.findByEmployeeEmail(email);

        assertNull(result);  // Assert that the result is null when employee does not exist
    }


    @Test
    void getEmployeeByTeamAndEmployeeRoleNot() {
        Team team= new Team(1,null,null,null,null);
        employeeService.getEmployeeByTeamAndEmployeeRoleNot(team,"MANAGER");
        verify(employeeRepository,times(1)).findByTeamAndEmployeeRoleNot(team,"MANAGER" );
    }
    @Test
    void loadUserByUsername(){
        when(employeeRepository.findByEmployeeEmail("username")).thenReturn(null);
       assertThrows(UsernameNotFoundException.class,()->{employeeService.loadUserByUsername("username");});

    }

}
