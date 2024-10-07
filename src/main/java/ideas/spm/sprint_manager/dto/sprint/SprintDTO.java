package ideas.spm.sprint_manager.dto.sprint;

import java.time.LocalDate;
import java.util.List;

public interface SprintDTO {
    int getSprintId();
    String getSprintName();
    LocalDate getSprintStart();
    String getSprintGoal();
    LocalDate getSprintEnd();
    Employee getSprintManager();
    List<Task> getTaskList();
    String getStatus();

    interface Team{
        int getTeamId();
        String getTeamName();
    }
    interface Employee{
        int getEmployeeID();
        String getEmployeeName();
    }
    interface Task{
        int getTaskId();
        String getTaskName();
        String getTaskStatus();
        float getStoryPoint();
        String getTaskDetails();
        Employee getTaskAssigned();
        Team getTaskTeam();
        Employee getTaskCreatedBy();
        LocalDate getTaskStarted();
        LocalDate getTaskDeadline();
    }

}
