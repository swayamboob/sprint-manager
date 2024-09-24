package ideas.spm.sprint_manager.service;

import com.sun.tools.jconsole.JConsoleContext;
import com.sun.tools.jconsole.JConsolePlugin;
import ideas.spm.sprint_manager.dto.sprint.SprintDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Manager;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.entity.Task;
import ideas.spm.sprint_manager.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {
    @Autowired
    SprintRepository sprintRepository;

    public List<SprintDTO> getSprints() {
        return sprintRepository.findBy();
    }
    public List<SprintDTO>getSprintCreatedbyManager( int managerId){
        return sprintRepository.findBySprintManager_employeeID(managerId);
    }


    public SprintDTO getSprintById(int sprintId) {
        return sprintRepository.findBySprintId(sprintId);
    }
    public SprintDTO getActiveSprint(int managerId){
        List<SprintDTO> listOfSprint= sprintRepository.findByStatusAndSprintManager_employeeID("active",managerId);
        if( listOfSprint.size()>0){
            return listOfSprint.get(0);
        }else{
            return null;
        }
    }
    public Sprint insertSprint(Sprint sprint) {
        boolean exist = sprintRepository.existsById(sprint.getSprintId());
        if (exist) {

        }
        return sprintRepository.save(sprint);
    }
    public boolean startSprint(int sprintId, int employeeId){
        SprintDTO activeSprint= getActiveSprint(employeeId);
        if(activeSprint==null){
            Sprint sprint= sprintRepository.findById(sprintId).get();
            sprint.setStatus("active");
            return true;
        }else{
            return false;
        }
    }
    public List<SprintDTO>getSprintByManager(int managerId){
        return sprintRepository.findBySprintManager(new Employee(managerId,null,null,null,null,null) );
    }

    public SprintDTO updateSprint(int sprintId, Sprint sprintDetails){
        // Find the sprint by ID
        if(sprintId==-1){
              sprintRepository.save(sprintDetails);
             return sprintRepository.findBySprintId(sprintId);
        }
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        if (optionalSprint.isPresent()) {
            // Get the existing sprint entity
            Sprint existingSprint = optionalSprint.get();

            // Update fields only if they are not null
            if (sprintDetails.getSprintName() != null) {
                existingSprint.setSprintName(sprintDetails.getSprintName());
            }
            if (sprintDetails.getSprintGoal() != null) {
                existingSprint.setSprintGoal(sprintDetails.getSprintGoal());
            }
            if (sprintDetails.getSprintStart() != null) {
                existingSprint.setSprintStart(sprintDetails.getSprintStart());
            }
            if (sprintDetails.getSprintEnd() != null) {
                existingSprint.setSprintEnd(sprintDetails.getSprintEnd());
            }
            if (sprintDetails.getSprintManager() != null) {
                existingSprint.setSprintManager(sprintDetails.getSprintManager());
            }
            if (sprintDetails.getStatus() != null) {
                existingSprint.setStatus(sprintDetails.getStatus());
            }

            // Save the updated sprint
             sprintRepository.save(existingSprint);
            return sprintRepository.findBySprintId(sprintId);
        } else {
            return null;
        }
    }
    public Integer deleteSprint(int sprintId) {
        return sprintRepository.deleteBySprintId(sprintId);
    }

//    public insertSprint()


}
