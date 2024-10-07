package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.task.TaskDTO;
import ideas.spm.sprint_manager.entity.*;
import ideas.spm.sprint_manager.exceptions.TaskExceptions.TaskNotFound;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import ideas.spm.sprint_manager.repository.SprintRepository;
import ideas.spm.sprint_manager.repository.TaskRepository;
import ideas.spm.sprint_manager.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    SprintRepository sprintRepository;

    @InjectMocks
    TaskService taskService;

    Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTaskId(1);
        task.setTaskName("Test Task");
        task.setTaskDetails("Test Task Details");
        task.setTaskStatus("Pending");
        task.setTaskAssigned(new Employee()); // Add more properties if needed
        task.setTaskCreatedBy(new Employee());
        task.setTaskSprint(new Sprint());
        task.setTaskTeam(new Team());
    }

    @Test
    void getTasks() {
        List<TaskDTO> tasks = new ArrayList<>();
        when(taskRepository.findBy()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getTasks();

        assertNotNull(result);
        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findBy();
    }

    @Test
    void findById_whenTaskExists() {
        when(taskRepository.existsById(1)).thenReturn(true);
        when(taskRepository.findByTaskId(1)).thenReturn(task);

        Task result = taskService.findById(1);

        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).findByTaskId(1);
    }

    @Test
    void findById_whenTaskDoesNotExist() {
        when(taskRepository.existsById(1)).thenReturn(false);

        Task result = taskService.findById(1);

        assertNull(result);
        verify(taskRepository, times(1)).existsById(1);
    }

    @Test
    void insertTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.insertTask(task);

        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTaskStatus_whenTaskExists() {
        when(taskRepository.existsById(1)).thenReturn(true);
        when(taskRepository.findByTaskId(1)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        int result = taskService.updateTaskStatus(1, "Completed");

        assertEquals(1, result);
        assertEquals("Completed", task.getTaskStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTaskStatus_whenTaskDoesNotExist() {
        when(taskRepository.existsById(1)).thenReturn(false);

        int result = taskService.updateTaskStatus(1, "Completed");

        assertEquals(0, result);
        verify(taskRepository, times(0)).save(task);
    }

    @Test
    void updateTask_whenTaskExists() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(employeeRepository.findById(anyInt())).thenReturn(new Employee()); // Mock the employee
        when(sprintRepository.findById(anyInt())).thenReturn(Optional.of(new Sprint())); // Mock the sprint
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        task.setStoryPoint(2);
        task.setTaskDeadline(LocalDate.now());
        task.setTaskStarted(LocalDate.now());
        Task result = taskService.updateTask(task);

        assertNull(result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTask_whenTaskDoesNotExist() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        Task result = taskService.updateTask(task);

        assertNull(result);
        verify(taskRepository, times(0)).save(task);
    }

    @Test
    void getBacklogs() {
        List<TaskDTO> tasks = new ArrayList<>();
        when(taskRepository.findByTaskCreatedBy_employeeIDAndTaskType(1, "BACKLOG")).thenReturn(tasks);

        List<TaskDTO> result = taskService.getBacklogs(1);

        assertNotNull(result);
        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findByTaskCreatedBy_employeeIDAndTaskType(1, "BACKLOG");
    }

    @Test
    void taskByEmployee() {
        Employee employee = new Employee();
        List<TaskDTO> tasks = new ArrayList<>();
        when(taskRepository.findByTaskAssigned(employee)).thenReturn(tasks);

        List<TaskDTO> result = taskService.taskByEmployee(employee);

        assertNotNull(result);
        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findByTaskAssigned(employee);
    }

    @Test
    void taskCreatedBy() {
        Employee createdBy = new Employee();
        List<TaskDTO> tasks = new ArrayList<>();
        when(taskRepository.findByTaskCreatedBy(createdBy)).thenReturn(tasks);

        List<TaskDTO> result = taskService.taskCreatedBy(createdBy);

        assertNotNull(result);
        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findByTaskCreatedBy(createdBy);
    }

    @Test
    void findTask_whenExists() {
        when(taskRepository.existsById(1)).thenReturn(true);

        boolean exists = taskService.findTask(task);

        assertTrue(exists);
        verify(taskRepository, times(1)).existsById(task.getTaskId());
    }

    @Test
    void findTask_whenDoesNotExist() {
        when(taskRepository.existsById(1)).thenReturn(false);

        boolean exists = taskService.findTask(task);

        assertFalse(exists);
        verify(taskRepository, times(1)).existsById(task.getTaskId());
    }

    @Test
    void closeSprintTask() {
        List<Task> tasks = Collections.singletonList(task);
        when(taskRepository.findByTaskSprint_sprintIdAndTaskCreatedBy_employeeID(1, 1)).thenReturn(tasks);
        when(sprintRepository.findBySprintIdAndStatus(1, "active")).thenReturn(new Sprint());
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        boolean result = taskService.closeSprintTask(1, 1);

        assertTrue(result);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(sprintRepository, times(1)).save(any(Sprint.class));
    }

    @Test
    void deleteTask_whenTaskExistsAndBelongsToEmployee() {
        when(taskRepository.findByTaskId(1)).thenReturn(task);
        when(taskRepository.deleteByTaskId(1)).thenReturn(1);

        ResponseEntity<?> result = taskService.deleteTask(1, task.getTaskCreatedBy().getEmployeeID());

        assertNotNull(result);
        assertEquals("task deleted successfully", ((Response) result.getBody()).getMsg());
        verify(taskRepository, times(1)).deleteByTaskId(1);
        when(taskRepository.deleteByTaskId(1)).thenReturn(0);
        assertThrows(TaskNotFound.class,()->{taskService.deleteTask(1,task.getTaskCreatedBy().getEmployeeID());});
    }

    @Test
    void deleteTask_whenTaskDoesNotExist() {
        when(taskRepository.findByTaskId(1)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            taskService.deleteTask(1, task.getTaskCreatedBy().getEmployeeID());
        });
        verify(taskRepository, times(0)).deleteByTaskId(1);
    }

    @Test
    void getMyTask() {
        List<TaskDTO> tasks = new ArrayList<>();
        when(taskRepository.findByTaskAssigned_employeeID(1)).thenReturn(tasks);

        List<TaskDTO> result = taskService.getMyTask(1);

        assertNotNull(result);
        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findByTaskAssigned_employeeID(1);
    }
}
