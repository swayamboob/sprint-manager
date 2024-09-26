package ideas.spm.sprint_manager.controllers;

import ideas.spm.sprint_manager.dto.team.TeamDTO;
import ideas.spm.sprint_manager.entity.Team;
import ideas.spm.sprint_manager.service.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    TeamService teamService;  // Mock the service

    @InjectMocks
    TeamController teamController;  // Inject mock service into the controller

    @Test
    void getTeams() {
        teamController.getTeams();
        verify(teamService, times(1)).getTeams();  // Verify that service method was called
    }

    @Test
    void getTeamByTeamId() {
        int teamId = 1;
        teamController.getTeamByTeamId(teamId);
        verify(teamService, times(1)).getTeamByTeamId(teamId);  // Verify that service method was called
    }

    @Test
    void getTeamsByManager() {
        int managerId = 1;
        teamController.getTeamsByManager(managerId);
        verify(teamService, times(1)).getTeamByManagerId(managerId);  // Verify that service method was called
    }

    @Test
    void insertTeam() {
        Team team = mock(Team.class);
        teamController.insertTeam(team);
        verify(teamService, times(1)).insertTeam(team);  // Verify that service method was called
    }

    @Test
    void deleteTeam() {
        int teamId = 1;
        when(teamService.deleteTeam(teamId)).thenReturn(1);
        assertEquals(1, teamController.deleteTeam(teamId));  // Ensure that service returns correct value
        verify(teamService, times(1)).deleteTeam(teamId);  // Verify that service method was called
    }
}
