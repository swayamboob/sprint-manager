package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.team.TeamDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import ideas.spm.sprint_manager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public List<TeamDTO> getTeams() {
        return teamRepository.findBy();
    }
    public TeamDTO getTeamByTeamId(int id){
        return teamRepository.findByTeamId(id);
    }
    public Team insertTeam(Team team) {
        boolean exist = teamRepository.existsById(team.getTeamId());
        if (exist) return null;
        return teamRepository.save(team);
    }
    public List<TeamDTO>getTeamByManagerId(int managerId){
        return teamRepository.findByTeamManager(new Employee(managerId,null,null,null,null,null));
    }

    public Integer deleteTeam(int teamid) {
        return teamRepository.deleteByTeamId(teamid);
    }
}
