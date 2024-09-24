package ideas.spm.sprint_manager.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int sprintId;
    String sprintName;
    String sprintGoal;
    LocalDate sprintStart;
    LocalDate sprintEnd;
    @ManyToOne // here made changes to oneto one to many-to-one
    @JoinColumn(name = "employeeid")
    Employee sprintManager;

    String status;
    @OneToMany(mappedBy = "taskSprint")
    List<Task> taskList;

    @PreRemove
    private void remove(){
        for(Task task:taskList){
            task.setTaskSprint(null);
        }
    }

    public Sprint() {

    }

    public Sprint(int sprintId, String sprintName, LocalDate sprintStart, LocalDate sprintEnd, Employee sprintManger, List<Task> taskList, String status) {
        this.sprintId = sprintId;
        this.sprintName = sprintName;
        this.sprintStart = sprintStart;
        this.sprintEnd = sprintEnd;
        this.sprintManager = sprintManger;
        this.taskList = taskList;
        this.status = status;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getSprintGoal() {
        return sprintGoal;
    }

    public void setSprintGoal(String sprintGoal) {
        this.sprintGoal = sprintGoal;
    }

    public LocalDate getSprintStart() {
        return sprintStart;
    }

    public void setSprintStart(LocalDate sprintStart) {
        this.sprintStart = sprintStart;
    }

    public LocalDate getSprintEnd() {
        return sprintEnd;
    }

    public void setSprintEnd(LocalDate sprintEnd) {
        this.sprintEnd = sprintEnd;
    }

    public Employee getSprintManager() {
        return sprintManager;
    }

    public void setSprintManager(Employee sprintManager) {
        this.sprintManager = sprintManager;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
