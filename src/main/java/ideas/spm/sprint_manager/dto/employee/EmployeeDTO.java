package ideas.spm.sprint_manager.dto.employee;

import java.util.List;

public interface EmployeeDTO {

    public int getEmployeeID();

    public String getEmployeeEmail();

    public String getEmployeeName();

    public String getEmployeePassword();

    public String getEmployeeRole();

    public Team getTeam();

    public List<Team> getManagerTeams();

    public interface Team {
        public int getTeamId();

        public String getTeamName();

        public Employee getTeamManager();
    }

    interface Employee {
        public String getEmployeeName();
    }
}
