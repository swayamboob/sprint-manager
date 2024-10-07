package ideas.spm.sprint_manager.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int taskId;
    String taskDetails;
    String taskName;
    float storyPoint;
    @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name="employeeid")
    Employee taskAssigned;
    // risky changes
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="created_by")
    Employee taskCreatedBy;
    //=========
    @ManyToOne
            @JoinColumn(name="sprint_id")
    Sprint taskSprint;
    String taskStatus;
    String taskType;
    LocalDate taskDeadline;
    LocalDate taskStarted;
    @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name="team_id")
    Team taskTeam;

    public Task(){
        this.taskType="FRESH";
    }
    public Task(int taskId, String taskDetails, Employee taskAssigned, Sprint taskSprint, String taskStatus, LocalDate taskDeadline, Team taskTeam) {
        this.taskId = taskId;
        this.taskDetails = taskDetails;
        this.taskAssigned = taskAssigned;
        this.taskSprint = taskSprint;
        this.taskStatus = taskStatus;
        this.taskDeadline = taskDeadline;
        this.taskTeam = taskTeam;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public float getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(float storyPoint) {
        this.storyPoint = storyPoint;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Employee getTaskAssigned() {
        return taskAssigned;
    }

    public void setTaskAssigned(Employee taskAssigned) {
        this.taskAssigned = taskAssigned;
    }

    public Sprint getTaskSprint() {
        return taskSprint;
    }

    public void setTaskSprint(Sprint taskSprint) {
        this.taskSprint = taskSprint;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDate taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public LocalDate getTaskStarted() {
        return taskStarted;
    }

    public void setTaskStarted(LocalDate taskStarted) {
        this.taskStarted = taskStarted;
    }

    public Team getTaskTeam() {
        return taskTeam;
    }

    public void setTaskTeam(Team taskTeam) {
        this.taskTeam = taskTeam;
    }

    public Employee getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(Employee taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }
}
