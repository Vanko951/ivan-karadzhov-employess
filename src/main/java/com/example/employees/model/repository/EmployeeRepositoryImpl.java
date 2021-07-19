package com.example.employees.model.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.employees.model.DataRecord;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{
	private List<DataRecord> database;

    public EmployeeRepositoryImpl() {
        this.database = new ArrayList<>();
    }

    @Override
    public void save(DataRecord record) {
        this.database.add(record);
    }

    @Override
    public void saveAll(Collection<DataRecord> records) {
        this.database.addAll(records);
    }

    public void deleteAll(Collection<DataRecord> records) {
		this.database.removeAll(records);
	}

	@Override
    public List<DataRecord> getAllRecords() {
        return Collections.unmodifiableList(this.database);
    }
}
