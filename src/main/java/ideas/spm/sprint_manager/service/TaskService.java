package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.task.TaskDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.entity.Task;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import ideas.spm.sprint_manager.repository.SprintRepository;
import ideas.spm.sprint_manager.repository.TaskRepository;
import ideas.spm.sprint_manager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SprintRepository sprintRepository;

    public List<TaskDTO> getTasks() {
        return taskRepository.findBy();
    }
    public Task findById( int taskId){
        boolean exist = taskRepository.existsById(taskId);
        if(exist) return taskRepository.findByTaskId(taskId);
        else return null;
    }

    public Task insertTask(Task task) {
        return taskRepository.save(task);
    }
    public Task updateTask(Task updatedTaskData){
        taskRepository.findById(updatedTaskData.getTaskId()).map(task -> {
                    // Update fields using setters
                    if (updatedTaskData.getTaskDetails() != null) {
                        task.setTaskDetails(updatedTaskData.getTaskDetails());
                    }
                    if (updatedTaskData.getTaskName() != null) {
                        task.setTaskName(updatedTaskData.getTaskName());
                    }
                    if (updatedTaskData.getTaskAssigned() != null) {
                        Employee assigned = employeeRepository.findById(updatedTaskData.getTaskAssigned().getEmployeeID());
                        task.setTaskAssigned(assigned);
                    }
                    if (updatedTaskData.getTaskCreatedBy() != null) {
                        Employee createdBy = employeeRepository.findById(updatedTaskData.getTaskCreatedBy().getEmployeeID());
                        task.setTaskCreatedBy(createdBy);
                    }
                    if (updatedTaskData.getTaskSprint() != null) {
                        Sprint sprint = sprintRepository.findById(updatedTaskData.getTaskSprint().getSprintId()).orElse(null);
                        task.setTaskSprint(sprint);
                    }
                    if (updatedTaskData.getTaskStatus() != null) {
                        task.setTaskStatus(updatedTaskData.getTaskStatus());
                    }
                    if (updatedTaskData.getTaskDeadline() != null) {
                        task.setTaskDeadline(updatedTaskData.getTaskDeadline());
                    }
                    if (updatedTaskData.getTaskStarted() != null) {
                        task.setTaskStarted(updatedTaskData.getTaskStarted());
                    }
                    if (updatedTaskData.getTaskTeam() != null) {
                        Team team = teamRepository.findById(updatedTaskData.getTaskTeam().getTeamId()).orElse(null);
                        task.setTaskTeam(team);
                    }
                    return taskRepository.save(task);
                });
        return null;
    }


    public List<TaskDTO> taskByEmployee(Employee task) {
        return taskRepository.findByTaskAssigned(task);

    }
    public List<TaskDTO> taskCreatedBy(Employee createdBy){
        return taskRepository.findByTaskCreatedBy(createdBy);
    }
    public boolean findTask(Task task) {
        return taskRepository.existsById(task.getTaskId());
    }
    public Integer deleteTask(int taskId, int employeeId){
        Task task= taskRepository.findByTaskId(taskId);
        if(task.getTaskCreatedBy().getEmployeeID()==employeeId){
            return taskRepository.deleteByTaskId(taskId);
        }
        return 0;
    }


}
