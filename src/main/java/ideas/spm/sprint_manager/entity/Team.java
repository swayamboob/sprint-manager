package ideas.spm.sprint_manager.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int teamId;

    @ManyToOne
    @JoinColumn(name = "employeeid")
    Employee teamManager;

    String teamName;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    List<Employee> teamMembers;    // not direct access via manager->team->teamMembers


    @OneToMany(mappedBy = "taskTeam", fetch = FetchType.EAGER)
    List<Task> teamTask;


    @PreRemove
    private void preRemove() {
        for (Employee employee : teamMembers) {
            employee.setTeam(null); // Nullify the Team in Employee
        }
        for(Task task:teamTask){
            task.setTaskTeam(null);
            task.setTaskAssigned(null);
        }
    }

    public Team(int teamId, Employee teamManager, String teamName, List<Employee> teamMembers, List<Task> teamTask) {
        this.teamId = teamId;
        this.teamManager = teamManager;
        this.teamName = teamName;
        this.teamMembers = teamMembers;
        this.teamTask = teamTask;
    }

    public Team() {

    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Employee getTeamManager() {
        return teamManager;
    }

    public void setTeamManager(Employee teamManager) {
        this.teamManager = teamManager;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Employee> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<Employee> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Task> getTeamTask() {
        return teamTask;
    }

    public void setTeamTask(List<Task> teamTask) {
        this.teamTask = teamTask;
    }
}
