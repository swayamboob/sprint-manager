package ideas.spm.sprint_manager.dto.team;

import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Task;

import java.util.List;

public interface TeamDTO {

    public int getTeamId();

    public String getTeamName();

    public Employee getTeamManager();

    public List<Employee> getTeamMembers();

    public List<Task> getTeamTask();

    interface Employee{
        public String getEmployeeName();
        public int getEmployeeID();
        public String getEmployeeRole();
        public String getEmployeeEmail();
    }
    interface Task{
        int getTaskId();
        String getTaskDetails();
    }

}
