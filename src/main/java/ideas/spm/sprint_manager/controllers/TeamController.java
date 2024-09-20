package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.team.TeamDTO;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.TeamRepository;
import ideas.spm.sprint_manager.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("team")
@CrossOrigin(origins = "*")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/all")
    List<TeamDTO> getTeams() {
        return teamService.getTeams();
    }
    @GetMapping("/{teamId}")
    TeamDTO getTeamByTeamId(@PathVariable int teamId){
        return teamService.getTeamByTeamId(teamId);
    }
    @GetMapping("/manager/{managerId}")
    List<TeamDTO> getTeamsByManager(@PathVariable int managerId){
        return teamService.getTeamByManagerId(managerId);
    }

    @PostMapping("insert")
    Team insertTeam(@RequestBody Team team) {
        return teamService.insertTeam(team);
    }

    @DeleteMapping("remove/{teamid}")
    Integer deleteTeam(@PathVariable int teamid) {
        return teamService.deleteTeam(teamid);
    }
}
