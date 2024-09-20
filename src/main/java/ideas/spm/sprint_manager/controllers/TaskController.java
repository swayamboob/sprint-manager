package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.task.TaskDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Task;
import ideas.spm.sprint_manager.repository.TaskRepository;
import ideas.spm.sprint_manager.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/task")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/manager/all")
    List<TaskDTO> getTask() {
        return taskService.getTasks();
    }

    @PostMapping("/employee/add")
    Task insertTask(@RequestBody Task task) {
        Task task_db = taskService.findById(task.getTaskId());
        if (task_db != null) {
            if (task_db.getTaskCreatedBy().getEmployeeID() == task.getTaskCreatedBy().getEmployeeID()) {
                return taskService.updateTask(task);
            } else {
                return null;
            }

        }
        taskService.insertTask(task);
        return task;
    }

    @GetMapping("/employee")
    List<TaskDTO> taskByEmployee(@RequestBody Employee employee) {
        return taskService.taskByEmployee(employee);
    }

    @DeleteMapping("/manager/remove/{taskId}/{employeeId}")
    public Integer deleteTask(@PathVariable int taskId,@PathVariable int employeeId) {
        System.out.println(taskId+" ---"+ employeeId);
        return taskService.deleteTask(taskId,employeeId);
    }

    @GetMapping("/employee/creator/{employeeId}")
    public List<TaskDTO> getTaskCreatedBy(@PathVariable int employeeId) {
        return taskService.taskCreatedBy(new Employee(employeeId, null, null, null, null, null));
    }


}
