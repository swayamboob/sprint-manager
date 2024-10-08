package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.team.TeamDTO;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.TeamRepository;
import ideas.spm.sprint_manager.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?>getTeamsByManager(@PathVariable int managerId){
        return ResponseEntity.ok(teamService.getTeamByManagerId(managerId));
    }

    @PostMapping("/manager/insert")
    ResponseEntity<?> insertTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.insertTeam(team));
    }

    @DeleteMapping("remove/{teamid}")
    ResponseEntity<?> deleteTeam(@PathVariable int teamid) {
        return ResponseEntity.ok(teamService.deleteTeam(teamid));
    }
}
