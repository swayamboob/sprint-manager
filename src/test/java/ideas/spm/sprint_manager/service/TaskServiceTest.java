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
import ideas.spm.sprint_manager.roles.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SprintRepository sprintRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private Employee employee;
    private Sprint sprint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee(1, "john.doe@example.com", "John Doe", "DEVELOPER", "password123", null);

        task = new Task();
        task.setTaskId(1);
        task.setTaskName("Sample Task");
        task.setTaskAssigned(employee);
        task.setTaskStatus("NEW");
        task.setTaskSprint(null);

        sprint = new Sprint();
        sprint.setSprintId(1);
        sprint.setSprintName("Sprint 1");
        sprint.setSprintStart(LocalDate.now().minusDays(1));
        sprint.setSprintEnd(LocalDate.now().plusDays(10));
        sprint.setStatus("ACTIVE");
    }

    @Test
    void shouldReturnAllTasks_whenGetTasksCalled() {
        // Mocking repository to return a list of tasks
        when(taskRepository.findBy()).thenReturn(Arrays.asList(mock(TaskDTO.class), mock(TaskDTO.class)));

        List<TaskDTO> tasks = taskService.getTasks();

        // Assert
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findBy();
    }

    @Test
    void shouldReturnTaskById_whenTaskExists() {
        // Mock taskRepository to return the task
        when(taskRepository.findByTaskId(1)).thenReturn(task);
        when(taskRepository.existsById(1)).thenReturn(true);

        Task foundTask = taskService.findById(1);

        // Assert
        assertNotNull(foundTask);
        assertEquals(1, foundTask.getTaskId());
        assertEquals("Sample Task", foundTask.getTaskName());
    }

    @Test
    void shouldReturnNull_whenTaskDoesNotExist() {
        // Mock taskRepository to return null for non-existent task
        when(taskRepository.existsById(999)).thenReturn(false);

        Task foundTask = taskService.findById(999);

        // Assert
        assertNull(foundTask);
    }

    @Test
    void shouldInsertTaskSuccessfully() {
        // Mock taskRepository to save the task
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task insertedTask = taskService.insertTask(task);

        // Assert
        assertNotNull(insertedTask);
        assertEquals(1, insertedTask.getTaskId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() {
        // Mock taskRepository to find and update the task
        when(taskRepository.existsById(1)).thenReturn(true);
        when(taskRepository.findByTaskId(1)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        int result = taskService.updateTaskStatus(1, "COMPLETED");

        // Assert
        assertEquals(1, result);
        assertEquals("COMPLETED", task.getTaskStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldReturnZero_whenUpdatingTaskStatusOfNonExistentTask() {
        // Mock taskRepository to return false for non-existent task
        when(taskRepository.existsById(999)).thenReturn(false);

        int result = taskService.updateTaskStatus(999, "COMPLETED");

        // Assert
        assertEquals(0, result);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void shouldGetBacklogsForManager() {
        // Mock taskRepository to return a list of backlogs
        when(taskRepository.findByTaskCreatedBy_employeeIDAndTaskType(1, "BACKLOG"))
                .thenReturn(Arrays.asList(mock(TaskDTO.class), mock(TaskDTO.class)));

        List<TaskDTO> backlogs = taskService.getBacklogs(1);

        // Assert
        assertNotNull(backlogs);
        assertEquals(2, backlogs.size());
        verify(taskRepository, times(1)).findByTaskCreatedBy_employeeIDAndTaskType(1, "BACKLOG");
    }

    @Test
    void shouldCloseSprintTaskSuccessfully() {
        // Mock taskRepository to return tasks related to sprint and employee
        Task incompleteTask = new Task();
        incompleteTask.setTaskStatus("In Progress");
        when(taskRepository.findByTaskSprint_sprintIdAndTaskCreatedBy_employeeID(1, 1))
                .thenReturn(Arrays.asList(incompleteTask));

        when(sprintRepository.findBySprintIdAndStatus(1, "active")).thenReturn(sprint);
        when(sprintRepository.save(any(Sprint.class))).thenReturn(sprint);

        // Call the service method
        boolean result = taskService.closeSprintTask(1, 1);

        // Assert
        assertTrue(result);
        assertEquals("completed", sprint.getStatus());
        verify(sprintRepository, times(1)).save(sprint);
        verify(taskRepository, times(1)).save(incompleteTask);
    }

    @Test
    void shouldDeleteTask_whenTaskExistsAndCreatedByEmployee() {
        // Mock taskRepository to find the task and verify employee
        Task task = new Task();
        task.setTaskId(1);
        Employee creator = new Employee();
        creator.setEmployeeID(1);
        task.setTaskCreatedBy(creator);

        when(taskRepository.findByTaskId(1)).thenReturn(task);
        when(taskRepository.deleteByTaskId(1)).thenReturn(1);

        // Call the service method
        Integer deletedCount = taskService.deleteTask(1, 1);

        // Assert
        assertEquals(1, deletedCount);
        verify(taskRepository, times(1)).deleteByTaskId(1);
    }

    @Test
    void shouldNotDeleteTask_whenTaskNotCreatedByEmployee() {
        // Mock taskRepository to find the task but wrong creator
        Task task = new Task();
        task.setTaskId(1);
        Employee creator = new Employee();
        creator.setEmployeeID(2);  // Task was created by a different employee
        task.setTaskCreatedBy(creator);

        when(taskRepository.findByTaskId(1)).thenReturn(task);

        // Call the service method
        Integer deletedCount = taskService.deleteTask(1, 1);

        // Assert
        assertEquals(0, deletedCount);
        verify(taskRepository, never()).deleteByTaskId(anyInt());
    }
}
