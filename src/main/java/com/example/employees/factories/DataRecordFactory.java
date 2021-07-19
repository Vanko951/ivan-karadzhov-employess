package com.example.employees.factories;

import java.time.LocalDate;

import com.example.employees.model.DataRecord;
import com.example.employees.utils.ConvertStringToLocalDate;

public final class DataRecordFactory {
	private static final String DEFAULT_DELIMITER = ", ";
    private static final String NULL_STR = "NULL";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    
    private DataRecordFactory() {
		// No-op; won't be called
	}
    
	public static DataRecord execute(String line) {
        String[] recordArgs = line.split(DEFAULT_DELIMITER);
        if (recordArgs.length != 4) {
            throw new IllegalArgumentException("Invalid data format.");
        }

        long emplID = Long.parseLong(recordArgs[INDEX_ZERO].trim());
        long projectID = Long.parseLong(recordArgs[INDEX_ONE].trim());
        LocalDate dateFrom = ConvertStringToLocalDate.convertStringToDate(recordArgs[INDEX_TWO]);

        LocalDate dateTo;
        if (recordArgs[INDEX_THREE] == null || NULL_STR.equals(recordArgs[INDEX_THREE])) {
            dateTo = LocalDate.now();
        } else {
            dateTo = ConvertStringToLocalDate.convertStringToDate(recordArgs[INDEX_THREE]);
        }

        return new DataRecord(
                emplID,
                projectID,
                dateFrom,
                dateTo
        );
    }
}
