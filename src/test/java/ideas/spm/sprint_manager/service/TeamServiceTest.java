package ideas.spm.sprint_manager.service;

import ideas.spm.sprint_manager.dto.team.TeamDTO;
import ideas.spm.sprint_manager.entity.Employee;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.repository.EmployeeRepository;
import ideas.spm.sprint_manager.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    TeamRepository teamRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTeams() {
        // Arrange
        List<TeamDTO> teamDTOList = new ArrayList<>();
        when(teamRepository.findBy()).thenReturn(teamDTOList);

        // Act
        List<TeamDTO> result = teamService.getTeams();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(teamRepository, times(1)).findBy();
    }

    @Test
    void testGetTeamByTeamId() {
        // Arrange
        int teamId = 1;
        TeamDTO teamDTO = mock(TeamDTO.class);
        when(teamRepository.findByTeamId(teamId)).thenReturn(teamDTO);

        // Act
        TeamDTO result = teamService.getTeamByTeamId(teamId);

        // Assert
        assertNotNull(result);
        verify(teamRepository, times(1)).findByTeamId(teamId);
    }

    @Test
    void testInsertTeamSuccess() {
        // Arrange
        Team team = new Team();
        team.setTeamId(1);
        when(teamRepository.existsById(team.getTeamId())).thenReturn(false);
        when(teamRepository.save(team)).thenReturn(team);

        // Act
        Team result = teamService.insertTeam(team);

        // Assert
        assertNotNull(result);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void testInsertTeamFailure() {
        // Arrange
        Team team = new Team();
        team.setTeamId(1);
        when(teamRepository.existsById(team.getTeamId())).thenReturn(true);

        // Act
        Team result = teamService.insertTeam(team);

        // Assert
        assertNull(result);
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void testGetTeamByManagerId() {
        // Arrange
        int managerId = 1;
        Employee manager = new Employee(managerId, "manager@test.com", "Manager", "ROLE_MANAGER", "password", null);
        List<TeamDTO> teamDTOList = new ArrayList<>();
        when(teamRepository.findByTeamManager(manager)).thenReturn(teamDTOList);

        // Act
        List<TeamDTO> result = teamService.getTeamByManagerId(managerId);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(teamRepository, times(1)).findByTeamManager(any(Employee.class));
    }

    @Test
    void testDeleteTeamSuccess() {
        // Arrange
        int teamId = 1;
        when(teamRepository.deleteByTeamId(teamId)).thenReturn(1);

        // Act
        Integer result = teamService.deleteTeam(teamId);

        // Assert
        assertEquals(1, result);
        verify(teamRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    void testDeleteTeamFailure() {
        // Arrange
        int teamId = 1;
        when(teamRepository.deleteByTeamId(teamId)).thenReturn(0);

        // Act
        Integer result = teamService.deleteTeam(teamId);

        // Assert
        assertEquals(0, result);
        verify(teamRepository, times(1)).deleteByTeamId(teamId);
    }
}
