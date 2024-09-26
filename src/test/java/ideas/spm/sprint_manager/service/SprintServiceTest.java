package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.sprint.SprintDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Sprint;
import ideas.spm.sprint_manager.repository.SprintRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SprintServiceTest {

    @Mock
    SprintRepository sprintRepository;  // Mock the SprintRepository

    @InjectMocks
    SprintService sprintService;  // Inject mocks into the SprintService

    @Test
    void getSprints() {
        List<SprintDTO> sprintList = Arrays.asList();
        when(sprintRepository.findBy()).thenReturn(sprintList);

        List<SprintDTO> result = sprintService.getSprints();

        assertEquals(0, result.size());  // Assert that we get the expected number of sprints
        verify(sprintRepository, times(1)).findBy();  // Verify that findBy was called
    }

    @Test
    void getSprintById() {
        int sprintId = 1;
        SprintDTO sprint = mock(SprintDTO.class);

        when(sprintRepository.findBySprintId(sprintId)).thenReturn(sprint);

        SprintDTO result = sprintService.getSprintById(sprintId);

        assertEquals(sprint, result);  // Assert that the retrieved sprint is as expected
        verify(sprintRepository, times(1)).findBySprintId(sprintId);  // Verify that findBySprintId was called
    }

    @Test
    void getActiveSprint_whenActiveSprintExists() {
        int managerId = 1;
        SprintDTO activeSprint = mock(SprintDTO.class);
        when(sprintRepository.findByStatusAndSprintManager_employeeID("active", managerId))
                .thenReturn(Arrays.asList(activeSprint));

        SprintDTO result = sprintService.getActiveSprint(managerId);

        assertNotNull(result);  // Assert that the active sprint is not null
        verify(sprintRepository, times(1)).findByStatusAndSprintManager_employeeID("active", managerId);  // Verify the call
    }

    @Test
    void getActiveSprint_whenNoActiveSprintExists() {
        int managerId = 1;
        when(sprintRepository.findByStatusAndSprintManager_employeeID("active", managerId))
                .thenReturn(Arrays.asList());

        SprintDTO result = sprintService.getActiveSprint(managerId);

        assertNull(result);  // Assert that the result is null
        verify(sprintRepository, times(1)).findByStatusAndSprintManager_employeeID("active", managerId);  // Verify the call
    }

    @Test
    void insertSprint_whenSprintDoesNotExist() {
        Sprint sprint = new Sprint();
        sprint.setSprintId(1);

        when(sprintRepository.existsById(sprint.getSprintId())).thenReturn(false);
        when(sprintRepository.save(sprint)).thenReturn(sprint);

        Sprint result = sprintService.insertSprint(sprint);

        assertNotNull(result);  // Assert that the saved sprint is not null
        verify(sprintRepository, times(1)).save(sprint);  // Verify save was called
    }

    @Test
    void insertSprint_whenSprintExists() {
        Sprint sprint = new Sprint();
        sprint.setSprintId(1);

        when(sprintRepository.existsById(sprint.getSprintId())).thenReturn(false);
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        Sprint result = sprintService.insertSprint(sprint);
        assertEquals(result,sprint);  // Assert that the result is null
        verify(sprintRepository, times(1)).save(sprint);  // Verify save was not called
    }

    @Test
    void updateSprint_whenSprintExists() {
        int sprintId = 1;
        Sprint sprintDetails = new Sprint();
        sprintDetails.setSprintName("Updated Sprint");

        Sprint existingSprint = new Sprint();
        existingSprint.setSprintId(sprintId);
        existingSprint.setSprintName("Original Sprint");

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(existingSprint));

        SprintDTO result = sprintService.updateSprint(sprintId, sprintDetails);

        assertNull(result);  // Assert that the updated sprint is not null
        assertEquals("Updated Sprint", existingSprint.getSprintName());  // Assert that the name was updated
        verify(sprintRepository, times(1)).save(existingSprint);  // Verify save was called
    }

    @Test
    void updateSprint_whenSprintDoesNotExist() {
        int sprintId = 1;
        Sprint sprintDetails = new Sprint();

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.empty());

        SprintDTO result = sprintService.updateSprint(sprintId, sprintDetails);

        assertNull(result);  // Assert that the result is null
        verify(sprintRepository, times(0)).save(any(Sprint.class));  // Verify save was not called
    }

    @Test
    void deleteSprint() {
        int sprintId = 1;
        when(sprintRepository.deleteBySprintId(sprintId)).thenReturn(1);

        Integer result = sprintService.deleteSprint(sprintId);

        assertEquals(1, result);  // Assert that the deletion result is as expected
        verify(sprintRepository, times(1)).deleteBySprintId(sprintId);  // Verify that deleteBySprintId was called
    }



    @Test
    void startSprint_whenActiveSprintExists() {
        int sprintId = 1;
        int employeeId = 1;
        List<SprintDTO>listOfSprint= new ArrayList<SprintDTO>();
        when(sprintRepository.findByStatusAndSprintManager_employeeID("active",employeeId)).thenReturn(listOfSprint);
        Sprint sprint= new Sprint();
        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sprint));
        boolean result = sprintService.startSprint(sprintId, employeeId);
        assertTrue(sprintService.startSprint(sprintId,employeeId));

    }

    @Test
    void getSprintCreatedByManager() {
        int managerId = 1;
        SprintDTO sprintDTO= mock(SprintDTO.class);
        List<SprintDTO> sprintList = Arrays.asList( sprintDTO,sprintDTO);
        when(sprintRepository.findBySprintManager_employeeID(managerId)).thenReturn(sprintList);

        List<SprintDTO> result = sprintService.getSprintCreatedbyManager(managerId);

        assertEquals(2, result.size());  // Assert that we get the expected number of sprints
        verify(sprintRepository, times(1)).findBySprintManager_employeeID(managerId);  // Verify the call
    }
}
