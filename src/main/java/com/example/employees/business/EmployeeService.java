package com.example.employees.business;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {

    void uploadFile(MultipartFile file);

	void printBestTeam(MultipartFile file);
}
