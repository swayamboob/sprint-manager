package ideas.spm.sprint_manager.dto.sprint;

import java.time.LocalDate;
import java.util.List;

public interface SprintDTO {
    int getSprintId();
    String getSprintName();
    LocalDate getSprintStart();
    LocalDate getSprintEnd();
    Employee getSprintManager();
    List<Task> getTaskList();
    String getStatus();

    interface Employee{
        int getEmployeeID();
        String getEmployeeName();
    }
    interface Task{
        int getTaskId();
        String getTaskDetails();
    }

}
