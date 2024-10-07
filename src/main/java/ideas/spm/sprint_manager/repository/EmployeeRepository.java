package ideas.spm.sprint_manager.repository;

import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.dto.employee.EmployeeDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Employee findById(int id);

    Employee findByEmployeeEmail(String employeeEmail);

    List<Employee> findByTeam(Team team);

    Integer deleteByEmployeeID(int employeeID);

    Optional<EmployeeDTO> findByEmployeeIDAndEmployeeEmail(int id, String employeeEmail);

    List<EmployeeDTO> findBy();
//    List<EmployeeDTO> findByManager()

    List<EmployeeDTO> findByEmployeeName(String employeeName);

    List<Employee> findByEmployeeRole(String employeeRole);

    // Find by team
    List<EmployeeDTO> findByTeamAndEmployeeRoleNot(Team team, String employeeRole);

}
