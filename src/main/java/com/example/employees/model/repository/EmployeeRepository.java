package com.example.employees.model.repository;

import java.util.Collection;
import java.util.List;

import com.example.employees.model.DataRecord;

public interface EmployeeRepository {
	void save(DataRecord record);

    void saveAll(Collection<DataRecord> records);
    
    void deleteAll(Collection<DataRecord> records);

    List<DataRecord> getAllRecords();
}
