package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.sprint.SprintDTO;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.service.SprintService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("sprint")
@CrossOrigin(origins = "*")
public class SprintController {
    @Autowired
    SprintService sprintService;
    @GetMapping("/manager/{managerId}")
    public List<SprintDTO> getAllSprint(@PathVariable int managerId){
        return sprintService.getSprints();
    }
    @PostMapping("/insert")
    public Sprint saveSprint(@RequestBody Sprint sprint){
        return sprintService.insertSprint(sprint);
    }
    @DeleteMapping("/remove/{sprintId}")
    public Integer deleteSprint(@PathVariable int sprintId){
        return sprintService.deleteSprint(sprintId);
    }
}
