package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<EmployeeDTO> getEmployee() {
        return employeeRepository.findBy();
    }

    public Employee saveEmployee(Employee employee) {
        boolean exists = employeeRepository.existsById(employee.getEmployeeID());
        if (exists) return null;
        return employeeRepository.save(employee);
    }
    public Employee updateEmployee(Employee employee){
        boolean exists= employeeRepository.existsById(employee.getEmployeeID());
        if(!exists)return null;
        return employeeRepository.save(employee);
    }
    public Employee getEmployeeById(int employeeId){
        return employeeRepository.findById(employeeId);
    }
    public EmployeeDTO getEmployeeByIdAndEmail(int id, String email){
        return employeeRepository.findByEmployeeIDAndEmployeeEmail(id,email);
    }




//    public List<EmployeeDTO>get(int managerId){
//        return employeeRepository.
//    }
    public List<EmployeeDTO>getEmployeeByTeamAndEmployeeRoleNot(Team team,String employeeRole){
        return employeeRepository.findByTeamAndEmployeeRoleNot(team,"MANAGER");
    }


    public Integer deleteEmployee(int employeeId) {
        return employeeRepository.deleteByEmployeeID(employeeId);
    }

    public boolean findEmployee(Employee employee) {
        return employeeRepository.existsById(employee.getEmployeeID());
    }
    public Employee findByEmployeeEmail(String employeeEmail){
        return employeeRepository.findByEmployeeEmail(employeeEmail);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Employee user = employeeRepository.findByEmployeeEmail(username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmployeeEmail())
                    .password(user.getEmployeePassword())
                    .roles(user.getEmployeeRole())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

    }
}
