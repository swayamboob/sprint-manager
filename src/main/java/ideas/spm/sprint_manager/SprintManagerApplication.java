package ideas.spm.sprint_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprintManagerApplication  {

 // TODO: complete globar Respos commneted

//    @Autowired
//    EmployeeRepository employeeRepository;
//    @Autowired
//    TeamRepository teamRepository;
//    @Autowired
//    TaskRepository taskRepository;
//    @Autowired
//    SprintRepository sprintRepository;

    public static void main(String[] args) {
        SpringApplication.run(SprintManagerApplication.class, args);
    }

// TODO: whole run method is commented with comment
//    @Override
//    public void run(String... args) throws Exception {
//
//        // TODO: fetching the Employee data usign DTO
//        List<EmployeeDTO> employeeDTOS= employeeRepository.findByEmployeeName("Sachin");
//        for(EmployeeDTO employeeDto: employeeDTOS){
//            System.out.println(employeeDto.getEmployeeName());
//            System.out.println(employeeDto.getTeam().getTeamName());
//        }
//
//		//TODO: adding new Team after manager
//
////        Team temp_team = new Team(0, new Employee(1, null, null, null, null), "G3", null, null);
////        teamRepository.save(temp_team);
//
//		// TODO: adding data to employee
//
////        employeeRepository.saveAll(Arrays.asList(new Employee[]{
////				new Employee(1, "Sachin", "MANAGER", "Password",
////						new Team(1,null,null,null,null)),
////				new Employee(2, "swayam", "FTE", "Password",
////						new Team(1,null,null,null,null)),
////				new Employee(3, "vaibhav", "FTE", "Password",
////						new Team(1,null,null,null,null)),
////		}));
//
//		//TODO: manager employee fetching
//
////        Employee manager = employeeRepository.findById(1);
////		List<Team> teams= manager.getManagerTeams();
////        for(Team t: teams){
////			System.out.println(t.getTeamName());
////		}
////		System.out.println(teamRepository.findById(manager.getTeam().getTeamId()).getTeamMembers());
////        System.out.println(manager.getManagerTeams());
//
//        // TODO: fetching team and then members
////		Team temp_team=teamRepository.findById(1);
////		System.out.println(temp_team.getTeamId());
////		System.out.println(temp_team.getTeamManager().getEmployeeName());
////		System.out.println(temp_team.getTeamName());
////		System.out.println(temp_team.getTeamMembers());
//
//        //TODO: adding new data
////        List<Team> temp= teamRepository.findByTeamManager(new Employee(1,null,null,null,null));
////        for(Team t:temp){
////            System.out.println(t.getTeamName());
////        }
//
//    }
}
