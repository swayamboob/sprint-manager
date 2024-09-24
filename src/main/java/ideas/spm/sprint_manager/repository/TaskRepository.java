package ideas.spm.sprint_manager.repository;

import ideas.spm.sprint_manager.dto.task.TaskDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.entity.Task;
import ideas.spm.sprint_manager.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task,Integer> {

    Task findByTaskId(int taskId);
    //get all
    List<TaskDTO>findBy();

    // Find by taskAssigned (Employee)
    List<TaskDTO> findByTaskAssigned(Employee taskAssigned);

    // Find by taskSprint (Sprint)
    List<Task> findByTaskSprint(Sprint taskSprint);

    //
    Integer deleteByTaskId(int taskId);

    // Find by sprintStatus
    List<Task> findByTaskStatus(String sprintStatus);

    // Find by taskDeadline
    List<Task> findByTaskDeadline(LocalDate taskDeadline);

    // Find by taskTeam (Team)
    List<Task> findByTaskTeam(Team taskTeam);

    // find by creator
    List<TaskDTO> findByTaskCreatedBy(Employee createBy);

    List<Task>findByTaskSprint_sprintIdAndTaskCreatedBy_employeeID(int sprintId, int employeeId);

    List<TaskDTO> findByTaskCreatedBy_employeeIDAndTaskType(int employeeID,String type);
}
