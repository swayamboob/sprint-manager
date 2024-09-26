package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.task.TaskDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Response;
import ideas.spm.sprint_manager.entity.Task;
import ideas.spm.sprint_manager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;

    @Test
    void getTask() {
        taskController.getTask();
        verify(taskService, times(1)).getTasks();
    }

    @Test
    void insertTask_existingTask() {
        Task task = mock(Task.class);
        when(taskService.findById(task.getTaskId())).thenReturn(task);
        taskController.insertTask(task);
        verify(taskService, times(1)).updateTask(task);
        verify(taskService, never()).insertTask(any(Task.class));
    }

    @Test
    void insertTask_newTask() {
        Task task = mock(Task.class);
        when(taskService.findById(task.getTaskId())).thenReturn(null);
        taskController.insertTask(task);
        verify(taskService, times(1)).insertTask(task);
        verify(taskService, never()).updateTask(any(Task.class));
    }

    @Test
    void updateTaskStatus_success() {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", "1");
        map.put("taskStatus", "Completed");

        when(taskService.updateTaskStatus(1, "Completed")).thenReturn(1);
        ResponseEntity<?> response = taskController.updateTaskStatus(map);
        assertEquals(ResponseEntity.ok(new Response("Task Status Updated")).getStatusCode(),response.getStatusCode() );
    }

    @Test
    void updateTaskStatus_failure() {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", "1");
        map.put("taskStatus", "Completed");

        when(taskService.updateTaskStatus(1, "Completed")).thenReturn(0);
        ResponseEntity<?> response = taskController.updateTaskStatus(map);

        assertEquals(ResponseEntity.status(404).body("Task-status  not Updated"), response);
    }

    @Test
    void taskByEmployee() {
        Employee employee = mock(Employee.class);
        taskController.taskByEmployee(employee);
        verify(taskService, times(1)).taskByEmployee(employee);
    }

    @Test
    void deleteTask() {
        int taskId = 1;
        int employeeId = 1;
        when(taskService.deleteTask(taskId, employeeId)).thenReturn(1);
        Integer result = taskController.deleteTask(taskId, employeeId);
        assertEquals(1, result);
    }

    @Test
    void getTaskCreatedBy() {
        int employeeId = 1;
        taskController.getTaskCreatedBy(employeeId);
        verify(taskService, times(1)).taskCreatedBy(any(Employee.class));
    }

    @Test
    void closeSprint() {
        int sprintId = 1;
        int employeeId = 1;
        when(taskService.closeSprintTask(sprintId, employeeId)).thenReturn(true);
        boolean result = taskController.closeSprint(sprintId, employeeId);
        assertEquals(true, result);
    }

    @Test
    void getBacklogs() {
        int employeeId = 1;
        taskController.getBacklogs(employeeId);
        verify(taskService, times(1)).getBacklogs(employeeId);
    }
}
