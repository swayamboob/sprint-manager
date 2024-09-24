package ideas.spm.sprint_manager.repository;

import ideas.spm.sprint_manager.dto.sprint.SprintDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Sprint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface SprintRepository extends CrudRepository<Sprint, Integer> {

//    Sprint findById(int sprintId);

    SprintDTO findBySprintId(int sprintId);

    Integer deleteBySprintId(int sprintId);
    List<SprintDTO> findBy();
    List<SprintDTO>findBySprintManager_employeeID(int employeeID);
    List<SprintDTO>findByStatusAndSprintManager_employeeID(String Status,int managerId);
    // Find by sprintStart
    List<SprintDTO> findBySprintStart(LocalDate sprintStart);

    // Find by sprintEnd
    List<SprintDTO> findBySprintEnd(LocalDate sprintEnd);

    // Find by sprintManger (Employee)
    List<SprintDTO> findBySprintManager(Employee sprintManager);

    // Find by status
    List<SprintDTO> findByStatus(String status);

    //find active status


   Sprint findBySprintIdAndStatus(int sprintId, String status);

}
