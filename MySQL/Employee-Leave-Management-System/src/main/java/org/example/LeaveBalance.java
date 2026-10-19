
package org.example;

public class LeaveBalance {

    private int employeeId;
    private int totalLeaves;
    private int usedLeaves;

    public LeaveBalance(int employeeId, int totalLeaves,
                        int usedLeaves) {
        this.employeeId = employeeId;
        this.totalLeaves = totalLeaves;
        this.usedLeaves = usedLeaves;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public int getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(int usedLeaves) {
        this.usedLeaves = usedLeaves;
    }

    public int getAvailableLeaves() {
        return totalLeaves - usedLeaves;
    }

    @Override
    public String toString() {
        return "LeaveBalance{" +
                "employeeId=" + employeeId +
                ", totalLeaves=" + totalLeaves +
                ", usedLeaves=" + usedLeaves +
                ", availableLeaves=" + getAvailableLeaves() +
                '}';
    }
}
