package ideas.spm.sprint_manager.exceptions;

import ideas.spm.sprint_manager.exceptions.EmployeeExceptions.EmployeeNotFoundException;
import ideas.spm.sprint_manager.exceptions.SprintException.SprintNotFound;
import ideas.spm.sprint_manager.exceptions.TaskExceptions.TaskNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> employeeNotFound(EmployeeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<String> taskNotFound(TaskNotFound ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SprintNotFound.class)
    public ResponseEntity<String>sprintNotFound(SprintNotFound ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

}