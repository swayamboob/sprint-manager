package ideas.spm.sprint_manager.dto.task;

import java.time.LocalDate;

public interface TaskDTO {
    public int getTaskId();

    String getTaskDetails();
    String getTaskName();
    float getStoryPoint();
    String getTaskStatus();

    LocalDate getTaskDeadline();
    LocalDate getTaskStarted();
    TaskSprint getTaskSprint();
    String getTaskType();
    TaskAssigned getTaskAssigned();
    TaskCreatedBy getTaskCreatedBy();

    TaskTeam getTaskTeam();

    interface TaskSprint {
        int getSprintId();
        String getSprintName();
    }

    interface TaskAssigned {
        int getEmployeeID();

        String getEmployeeName();
    }
    interface TaskCreatedBy {
        int getEmployeeID();
        String getEmployeeName();
    }

    interface TaskTeam {
        int getTeamId();

        String getTeamName();
    }


}
