package com.example.employees.business;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.employees.model.DataRecord;

public interface EmployeeService {

    void uploadFile(MultipartFile file);
    
    void addEmployeeRecords(List<DataRecord> records);

	void printBestTeam(MultipartFile file);
}
