package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.sprint.SprintDTO;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.service.SprintService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllSprint(@PathVariable int managerId){
         return ResponseEntity.ok(sprintService.getSprintCreatedbyManager(managerId));
    }
    @GetMapping("/manager/active/{managerId}")
    public ResponseEntity<?> getSprintActive(@PathVariable int managerId){
        return ResponseEntity.ok(sprintService.getActiveSprint(managerId));
    }


    @PostMapping("/manager/insert")
    public ResponseEntity<?> saveSprint(@RequestBody Sprint sprint){
        return ResponseEntity.ok(sprintService.insertSprint(sprint));
    }
    @PutMapping("/manager/update/{sprintId}")
    public ResponseEntity<?> updateSprint(@PathVariable int sprintId, @RequestBody Sprint sprint){
        return ResponseEntity.ok(sprintService.updateSprint(sprintId,sprint));
    }

    @GetMapping("/manager/start/{sprintId}/{employeeId}")
    public ResponseEntity<?> startSprint(@PathVariable int sprintId,@PathVariable int employeeId){
        return ResponseEntity.ok( sprintService.startSprint(sprintId,employeeId));
    }
    @GetMapping("/manager/get/{sprintId}")
    public ResponseEntity<?> getSprintById(@PathVariable int sprintId){
        return ResponseEntity.ok( sprintService.getSprintById(sprintId));
    }
    @DeleteMapping("/manager/remove/{sprintId}")
    public ResponseEntity<?> deleteSprint(@PathVariable int sprintId){
        return ResponseEntity.ok((sprintService.deleteSprint(sprintId)==0?false:true)) ;
    }
}
