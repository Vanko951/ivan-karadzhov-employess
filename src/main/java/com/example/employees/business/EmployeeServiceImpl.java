package com.example.employees.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.employees.exceptions.FileStorageException;
import com.example.employees.factories.DataRecordFactory;
import com.example.employees.factories.TeamFactory;
import com.example.employees.model.DataRecord;
import com.example.employees.model.Team;
import com.example.employees.model.repository.EmployeeRepository;
import com.example.employees.utils.FileIO;
import com.example.employees.utils.FileIOImpl;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Value("${app.upload.dir:${user.home}}")
    private String uploadDir;
	
	private EmployeeRepository employeeRepository;
	private FileIO fileIO;

	@Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    @Override
    public void uploadFile(MultipartFile file) {

        try {
            Path copyLocation = Paths
                .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                + ". Please try again!");
        }
    }
    
    @Override
    public void printBestTeam(MultipartFile file) {
    	//Read all records data from uploaded file file
    	fileIO = new FileIOImpl();
    	List<DataRecord> records = new ArrayList<DataRecord>();
		try {
			records = fileIO.read(file.getInputStream())
			            .stream()
			            .map(DataRecordFactory::execute)
			            .collect(Collectors.toList());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addEmployeeRecords(records);
    	List<Team> teams = findAllTeamsWithOverlap();

        printResult(teams);
        
        employeeRepository.deleteAll(records);
    }
    
    /** Method which save all records to the database using EmployeeRepository */
    @Override
    public void addEmployeeRecords(List<DataRecord> records) {
        employeeRepository.saveAll(records);
    }
    
	/** 
	 * Method which finding all teams,
     * couples which have overlap and save them into List<Team> 
     */
    private List<Team> findAllTeamsWithOverlap() {
        List<DataRecord> allRecords = employeeRepository.getAllRecords();

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < allRecords.size() - 1; i++) {
            for (int j = i + 1; j < allRecords.size(); j++) {
            	DataRecord firstEmployee = allRecords.get(i);
            	DataRecord secondEmployee = allRecords.get(j);

                if (firstEmployee.getProjectId() == secondEmployee.getProjectId()
                        && hasOverlap(firstEmployee, secondEmployee)) {
                    long overlapDays = calculateOverlap(firstEmployee, secondEmployee);

                    if (overlapDays > 0) {
                        updateTeamCollection(teams, firstEmployee, secondEmployee, overlapDays);
                    }
                }
            }
        }
        return teams;
    }
    
    /** 
     * Method which calculating the total overlap and returning it 
     */
    private long calculateOverlap(DataRecord firstEmployee, DataRecord secondEmployee) {
        LocalDate periodStartDate =
        		firstEmployee.getDateFrom().isBefore(secondEmployee.getDateFrom()) ?
                		secondEmployee.getDateFrom() : firstEmployee.getDateFrom();

        LocalDate periodEndDate =
        		firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo()) ?
        				firstEmployee.getDateTo() : secondEmployee.getDateTo();

        return Math.abs(ChronoUnit.DAYS.between(periodStartDate, periodEndDate));
    }
    
    /** 
     * hasOverlap method returning if two employees have overlap 
     */
    private boolean hasOverlap(DataRecord firstEmployee, DataRecord secondEmployee) {
        return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo())
                || firstEmployee.getDateFrom().isEqual(secondEmployee.getDateTo()))
                && (firstEmployee.getDateTo().isAfter(secondEmployee.getDateFrom())
                || firstEmployee.getDateTo().isEqual(secondEmployee.getDateFrom()));
    }
    
    /** 
     * method check and returning if the current team is already present in team collection
     * (worked together under others projects) 
     */
    private boolean isTeamPresent(Team team, long firstEmployee, long secondEmployee) {
        return ( team.getFirstEmployeeId() == firstEmployee
                && team.getSecondEmployeeId() == secondEmployee )
                || ( team.getFirstEmployeeId() == secondEmployee
                && team.getSecondEmployeeId() == firstEmployee );
    }
    
    /** 
     * If the team is already present, it's total overlap duration will be updated with the new value,
     * otherwise will be create and add new team with the current data 
     */
    private void updateTeamCollection(List<Team> teams, DataRecord firstEmployee, DataRecord secondEmployee, long overlapDays) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        
        teams.forEach(team -> {
            if (isTeamPresent(team, firstEmployee.getEmployeeId(), secondEmployee.getEmployeeId())) {
                team.addOverlapDuration(overlapDays);
                isPresent.set(true);
            }
        });

        if (!isPresent.get()) {
            Team newTeam = TeamFactory.execute(
            		firstEmployee.getEmployeeId(),
            		secondEmployee.getEmployeeId(),
                    overlapDays);
            teams.add(newTeam);
        }
    }

	/**
	 * If don't have couple of employees which are worked together under same project
	 * will be print message with text "Doesn't exist teams", otherwise
	 * will be find and print the team with best overlap under their joint projects.
	 **/
	private void printResult(List<Team> teams) {
	    if (teams.size() != 0) {
	        teams.sort((team1, team2) ->
	                (int) (team2.getTotalDuration() - team1.getTotalDuration()));
	        Team bestTeam = teams.get(0);
	
	        System.out.println(
	                String.format("The pair of employees that has worked the longest on projects:%n employeeID: %d & employeeID: %d with total overlap duration %d days",
	                        bestTeam.getFirstEmployeeId(),
	                        bestTeam.getSecondEmployeeId(),
	                        bestTeam.getTotalDuration()));
	    } else {
	        System.out.println("Doesn't exist pair of employees which are worked together on projects.");
	    }
	}
}
