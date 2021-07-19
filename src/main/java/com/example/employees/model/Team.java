package com.example.employees.model;

public class Team {
	private long firstEmployeeId;
    private long secondEmployeeId;
    private long totalDuration;

    public Team(long firstEmployeeId, long secondEmployeeId, long totalDuration) {
        this.setFirstEmployeeId(firstEmployeeId);
        this.setSecondEmployeeId(secondEmployeeId);
        this.setTotalDuration(totalDuration);
    }

	public long getFirstEmployeeId() {
		return firstEmployeeId;
	}

	public void setFirstEmployeeId(long firstEmployeeId) {
		this.firstEmployeeId = firstEmployeeId;
	}

	public long getSecondEmployeeId() {
		return secondEmployeeId;
	}

	public void setSecondEmployeeId(long secondEmployeeId) {
		this.secondEmployeeId = secondEmployeeId;
	}

	public long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}
	
	public void addOverlapDuration(long overlap) {
        this.totalDuration += overlap;
    }
}
