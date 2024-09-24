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
    @GetMapping("/all/manager/{managerId}")
    public List<SprintDTO> getAllSprint(@PathVariable int managerId){
        return sprintService.getSprintCreatedbyManager(managerId);
    }
    @GetMapping("/manager/active/{managerId}")
    public SprintDTO getSprintActive(@PathVariable int managerId){
        return sprintService.getActiveSprint(managerId);
    }


    @PostMapping("/manager/insert")
    public Sprint saveSprint(@RequestBody Sprint sprint){
        return sprintService.insertSprint(sprint);
    }
    @PutMapping("/manager/update/{sprintId}")
    public SprintDTO updateSprint(@PathVariable int sprintId, @RequestBody Sprint sprint){
        return sprintService.updateSprint(sprintId,sprint);
    }

    @GetMapping("/manager/start/{sprintId}/{employeeId}")
    public boolean startSprint(@PathVariable int sprintId,@PathVariable int employeeId){
        return sprintService.startSprint(sprintId,employeeId);
    }
    @GetMapping("/manager/get/{sprintId}")
    public SprintDTO getSprintById(@PathVariable int sprintId){
        return sprintService.getSprintById(sprintId);
    }
    @DeleteMapping("/manager/remove/{sprintId}")
    public boolean deleteSprint(@PathVariable int sprintId){
        return (sprintService.deleteSprint(sprintId)==0?false:true) ;
    }
}
