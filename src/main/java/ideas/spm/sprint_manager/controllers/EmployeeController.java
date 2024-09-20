package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.employee.EmployeeLoginDTO;
import ideas.spm.sprint_manager.dto.employee.UserDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Response;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import ideas.spm.sprint_manager.service.EmployeeService;
import ideas.spm.sprint_manager.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Transactional
@CrossOrigin(origins = "*")
//@RequestMapping("/")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/manager/all")
    public List<EmployeeDTO> getAllEmployee() {
        return employeeService.getEmployee();
    }

    @GetMapping("/manager/employee/idle")
    public List<EmployeeDTO> getMyEmployee() {
        return employeeService.getEmployeeByTeamAndEmployeeRoleNot(null, "MANAGER");
    }

    @PostMapping("/manager/employee/update/team/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable int employeeId, @RequestBody Map<String, String> changes) {
        Employee e = employeeService.getEmployeeById(employeeId);
        if (e == null) return ResponseEntity.notFound().build();
        if (changes.containsKey("teamId")) {
            if (e.getTeam() != null)
                return ResponseEntity.status(404).body(new Response("Alread Present in other team"));
            e.setTeam(new Team(Integer.parseInt(changes.get("teamId")), null, null, null, null));
            if (employeeService.updateEmployee(e) != null) {
                return ResponseEntity.ok(e);
            } else {
                return ResponseEntity.status(404).body(new Response("Error Occured"));
            }
        } else {
            return ResponseEntity.status(404).body(new Response("Error Occured"));
        }

    }

    @PostMapping("/register")
    public Employee saveEmployee(@RequestBody Employee employee) {
        boolean exists = employeeService.findEmployee(employee);
        if (exists) return null;
        employee.setEmployeePassword(bCryptPasswordEncoder.encode(employee.getEmployeePassword()));
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return savedEmployee;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody EmployeeLoginDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmployeeEmail(), loginRequest.getEmployeePassword())
        );
        UserDetails userDetails = employeeService.loadUserByUsername(loginRequest.getEmployeeEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        Employee employee = employeeService.findByEmployeeEmail(userDetails.getUsername());
        record employeeProfile(String employeeName, int employeeID, String employeeEmail) {
        }
        ;
        record EmployeeDetails(employeeProfile employeeProfile, String jwt) {
        }
        ;
        return ResponseEntity.ok(new EmployeeDetails(new employeeProfile(employee.getEmployeeName(), employee.getEmployeeID(), employee.getEmployeeEmail()), jwt));
    }

    //    @PutMapping("/update/")
    @DeleteMapping("/remove/{employeeid}")
    public Integer deleteEmployee(@PathVariable int employeeid) {
        Integer deletedObject = employeeService.deleteEmployee(employeeid);
        return deletedObject;
    }


    @GetMapping("/employee/profile/{employeeId}/{employeeEmail}")
    public EmployeeDTO getProfile(@PathVariable int employeeId, @PathVariable String employeeEmail) {
        return employeeService.getEmployeeByIdAndEmail(employeeId, employeeEmail);
    }


}
