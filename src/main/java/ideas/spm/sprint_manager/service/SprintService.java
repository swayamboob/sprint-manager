package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.sprint.SprintDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Manager;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {
    @Autowired
    SprintRepository sprintRepository;

    public List<SprintDTO> getSprints() {
        return sprintRepository.findBy();
    }

    public SprintDTO getSprintById(int sprintId) {
        return sprintRepository.findBySprintId(sprintId);
    }

    public Sprint insertSprint(Sprint sprint) {
        boolean exist = sprintRepository.existsById(sprint.getSprintId());
        if (exist) return null;
        return sprintRepository.save(sprint);
    }
    public List<SprintDTO>getSprintByManager(int managerId){
        return sprintRepository.findBySprintManager(new Employee(managerId,null,null,null,null,null) );
    }


    public Integer deleteSprint(int sprintId) {
        return sprintRepository.deleteBySprintId(sprintId);
    }
//    public insertSprint()


}
