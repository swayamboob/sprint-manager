package ideas.spm.sprint_manager.exceptions.TaskExceptions;


public class TaskNotFound  extends RuntimeException{
    public TaskNotFound(String message) {
        super(message);
    }
}