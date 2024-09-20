package ideas.spm.sprint_manager.repository;

import ideas.spm.sprint_manager.dto.team.TeamDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
//    Team findById(int id);

    // Find by teamId
    TeamDTO findByTeamId(int teamId);

    // Find by teamName
    List<TeamDTO> findByTeamName(String teamName);

    // Find by teamManager
    List<TeamDTO> findByTeamManager(Employee teamManager);

    //all get
    List<TeamDTO> findBy();
    // delete Team by id
    Integer deleteByTeamId(int teamId);
}
