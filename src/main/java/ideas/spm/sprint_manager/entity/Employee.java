package ideas.spm.sprint_manager.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int employeeID;
    String employeeEmail;
    String employeeName;
    String employeeRole;
    String employeePassword;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;
    @OneToMany(mappedBy = "teamManager",fetch = FetchType.EAGER)
    List<Team> managerTeams;
    @OneToMany(mappedBy = "taskAssigned")
    List<Task>EmployeeTasks;

    public List<Team> getManagerTeams() {
        return managerTeams;
    }

//    public void setManagerTeam(List<Team> managerTeam) {
//        this.managerTeams = managerTeam;
//    }

    public Employee() {

    }

    public Employee(int employeeID,String employeeEmail, String employeeName, String employeeRole, String employeePassword, Team team) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeRole = employeeRole;
        this.employeePassword = employeePassword;
        this.team = team;
        this.employeeEmail=employeeEmail;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public void setManagerTeams(List<Team> managerTeams) {
        this.managerTeams = managerTeams;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Task> getEmployeeTasks() {
        return EmployeeTasks;
    }

    public void setEmployeeTasks(List<Task> employeeTasks) {
        EmployeeTasks = employeeTasks;
    }
}
