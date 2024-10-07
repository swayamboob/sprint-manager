package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.task.TaskDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Response;
import ideas.spm.sprint_manager.entity.Task;
import ideas.spm.sprint_manager.repository.TaskRepository;
import ideas.spm.sprint_manager.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
                return taskService.updateTask(task);
        }
        taskService.insertTask(task);
        return task;
    }
    @PutMapping("/employee/update/status")
    ResponseEntity<?> updateTaskStatus(@RequestBody Map<String,String>mp){
        if(taskService.updateTaskStatus(Integer.parseInt(mp.get("taskId")),mp.get("taskStatus"))==1)return ResponseEntity.ok(new Response("Task Status Updated"));
        else return ResponseEntity.status(404).body("Task  not Updated");
    }

    @GetMapping("/employee/mytask/{employeeId}")
    List<TaskDTO> getMyTask(@PathVariable int employeeId){
        return taskService.getMyTask(employeeId);
    }


    @GetMapping("/employee")
    List<TaskDTO> taskByEmployee(@RequestBody Employee employee) {
        return taskService.taskByEmployee(employee);
    }

    @DeleteMapping("/manager/remove/{taskId}/{employeeId}")
    public ResponseEntity<?> deleteTask(@PathVariable int taskId,@PathVariable int employeeId) {
        return taskService.deleteTask(taskId,employeeId);
    }

    @GetMapping("/employee/creator/{employeeId}")
    public List<TaskDTO> getTaskCreatedBy(@PathVariable int employeeId) {
        return taskService.taskCreatedBy(new Employee(employeeId, null, null, null, null, null));
    }
    @GetMapping("/manager/close/sprint/{sprintId}/{employeeId}")
    public boolean closeSprint(@PathVariable int sprintId, @PathVariable int employeeId){
        return taskService.closeSprintTask(sprintId,employeeId);
    }
    @GetMapping("/manager/backlogs/{employeeId}")
    public List<TaskDTO> getBacklogs(@PathVariable int employeeId){
        return taskService.getBacklogs(employeeId);
    }


}
