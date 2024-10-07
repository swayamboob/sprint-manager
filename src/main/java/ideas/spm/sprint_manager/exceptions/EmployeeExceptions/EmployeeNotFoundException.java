package ideas.spm.sprint_manager.exceptions.EmployeeExceptions;

public class EmployeeNotFoundException  extends RuntimeException{
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
