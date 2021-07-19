package com.example.employees.factories;

import com.example.employees.model.Team;

public final class TeamFactory {
	private TeamFactory() {
		// No-op; won't be called
	}
	public static Team execute(long firstEmployeeId, long secondEmployeeId, long overlapDuration) {
        return new Team(
                firstEmployeeId,
                secondEmployeeId,
                overlapDuration);
    }
}
