package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import ideas.spm.sprint_manager.dto.employee.EmployeeLoginDTO;
import ideas.spm.sprint_manager.dto.employee.UserDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Response;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.service.EmployeeService;
import ideas.spm.sprint_manager.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    PasswordEncoder bCyptPasswordEncoder;


    @Test
    void getAllEmployee() {
        List<EmployeeDTO> employeeDTOList= new ArrayList();
        when(employeeService.getEmployee()).thenReturn(employeeDTOList);
        employeeController.getAllEmployee();
        verify(employeeService,times(1)).getEmployee();
    }

    @Test
    void getMyEmployee() {
        List<EmployeeDTO>employeeDTOList= new ArrayList<>();
                when(employeeService.getEmployeeByTeamAndEmployeeRoleNot(null, "MANAGER")).thenReturn(employeeDTOList);
                List<EmployeeDTO> list= employeeController.getMyEmployee();
                assertEquals(list,employeeDTOList);
    }

    @Test
    void updateEmployee() {
        Employee employee= mock(Employee.class);
        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        HashMap<String,String> map= new HashMap<>();
        map.put("teamId","2");
        when(employee.getTeam()).thenReturn(null);
        employeeController.updateEmployee(1,map);
        verify(employeeService,times(1)).updateEmployee(employee);
//        ResponseEntity.status(404).body(new Response("Alread Present in other team"));
        when(employee.getTeam()).thenReturn(new Team(1,null,null,null,null));
        assertEquals(employeeController.updateEmployee(1,map).getStatusCode(),HttpStatus.NOT_FOUND);

        when(employeeService.updateEmployee(employee)).thenReturn(employee);
        when(employee.getTeam()).thenReturn(null);
        assertEquals(employeeController.updateEmployee(1,map).getStatusCode(),HttpStatus.OK);
    }

    @Test
    void saveEmployee() {
        Employee employee = mock(Employee.class);
        when(employeeService.findEmployee(employee)).thenReturn(true);
        when(employee.getEmployeePassword()).thenReturn("password");
        assertNull(employeeController.saveEmployee(employee));
        verify(employeeService, times(1)).findEmployee(employee);
        when(employeeService.findEmployee(employee)).thenReturn(false);
        employeeController.saveEmployee(employee);
        bCyptPasswordEncoder= mock(PasswordEncoder.class);
//        when(bCyptPasswordEncoder.encode(employee.getEmployeePassword())).thenReturn("xxx");
        verify(employee,times(1)).setEmployeePassword(bCyptPasswordEncoder.encode(employee.getEmployeePassword()));
        verify(employeeService,times(1)).saveEmployee(employee);
    }

    @Test
    void loginUser() {
        EmployeeLoginDTO employee = new EmployeeLoginDTO();
        employee.setEmployeeEmail("swayam@gmail.com");
        employee.setEmployeePassword("password");
//        when(employee.getEmployeeName()).thenReturn("swayam");
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getEmployeeEmail(),employee.getEmployeePassword()))).thenReturn(null);
        when(employeeService.loadUserByUsername("swayam@gmail.com")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("fakeJwt");
        Employee newemployee= mock(Employee.class);
        when(employeeService.findByEmployeeEmail(userDetails.getUsername())).thenReturn(newemployee);
        ResponseEntity<?> responseEntity = employeeController.loginUser(employee);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(employeeService, times(1)).loadUserByUsername("swayam@gmail.com");
    }

    @Test
    void deleteEmployee() {
        int employeeid=1;
       when( employeeService.deleteEmployee(employeeid)).thenReturn(1);
       employeeController.deleteEmployee(employeeid);
       verify(employeeService,times(1)).deleteEmployee(employeeid);
    }

    @Test
    void getProfile() {
        employeeController.getProfile(0,"swayamboob@gmail.com");
        verify(employeeService,times(1)).getEmployeeByIdAndEmail(0, "swayamboob@gmail.com");
    }
}