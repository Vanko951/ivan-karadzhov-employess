package com.example.employes.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.employees.business.EmployeeService;
import com.example.employees.controller.FileController;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {
	    @InjectMocks
	    FileController fileController;
	     
	    @Mock
	    EmployeeService employeeService;
	    
	    @Mock
	    RedirectAttributes flashAttributes;
	     
	    @Test
	    @DisplayName("Test controller.")
	    public void ShouldReturnString_whenUploadFile_WithMultipartFile() 
	    {
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	         
	        MockMultipartFile firstFile = new MockMultipartFile("data", "employee.txt", "text/plain", "some xml".getBytes());
	        String response = fileController.uploadFile(firstFile, flashAttributes);
	         
	        assertNotNull(response);
	    }
	}
