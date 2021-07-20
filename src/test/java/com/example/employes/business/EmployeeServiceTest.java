package com.example.employes.business;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.powermock.modules.junit4.PowerMockRunner;

import com.example.employees.business.EmployeeService;
import com.example.employees.business.EmployeeServiceImpl;
import com.example.employees.exceptions.FileStorageException;
import com.example.employees.model.repository.EmployeeRepository;
import com.example.employees.model.repository.EmployeeRepositoryImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
public class EmployeeServiceTest {

	@Mock
	EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
	
	@InjectMocks
	EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
	
	@Test
	void shouldReturnFileStorageException_whenUploadFile_givenMultipartFile() {
		MockMultipartFile firstFile = new MockMultipartFile("data", "employees.txt", "text/plain", "some xml".getBytes());
		
		assertThatThrownBy(() ->
		{
			employeeService.uploadFile(firstFile);
		}).isInstanceOf(FileStorageException.class);
	}
	
}
