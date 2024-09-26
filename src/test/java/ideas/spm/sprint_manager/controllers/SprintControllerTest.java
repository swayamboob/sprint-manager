package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.repository.SprintRepository;
import ideas.spm.sprint_manager.service.SprintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SprintControllerTest {
    @Mock
    SprintService sprintService;
    @InjectMocks
    SprintController sprintController;
    @Test
    void getAllSprint() {
        sprintController.getAllSprint(0);
        verify(sprintService,times(1)).getSprintCreatedbyManager(0);
    }

    @Test
    void getSprintActive() {
        sprintController.getSprintActive(0);
        verify(sprintService,times(1)).getActiveSprint(0);
    }

    @Test
    void saveSprint() {
        Sprint sprint= mock(Sprint.class);
        sprintController.saveSprint(sprint);
        verify(sprintService,times(1)).insertSprint(sprint);
    }

    @Test
    void updateSprint() {
        int sprintId=0;
        Sprint sprint= mock(Sprint.class);
        sprintController.updateSprint(sprintId,sprint);
        verify(sprintService).updateSprint(sprintId,sprint);
    }

    @Test
    void startSprint() {
        int sprintId=0,employeeId=0;
        sprintController.startSprint(sprintId,employeeId);
        verify(sprintService,times(1)).startSprint(sprintId,employeeId);
    }

    @Test
    void getSprintById() {
        int sprintId=0;
        sprintController.getSprintById(sprintId);
        verify(sprintService).getSprintById(sprintId);
    }

    @Test
    void deleteSprint() {
        int sprintId=0;
        when(sprintService.deleteSprint(sprintId)).thenReturn(1);
        assertEquals(sprintController.deleteSprint(sprintId),true);
    }
}