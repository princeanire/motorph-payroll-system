package motorph;

import java.util.List;

public class App {
    public static void main(String[] args) {
        EmployeeDatabaseService.getAllAttendanceRecords();
        List<AttendanceRecord> antonioLimAttendanceRecords = EmployeeDatabaseService
                .getAttendanceRecordsByEmployeeId(10002);
        for (AttendanceRecord attendanceRecord : antonioLimAttendanceRecords) {
            System.out.println(attendanceRecord.employeeId);
        }

        EmployeeDatabaseService.getAllEmployeeDetails();
        Employee antonioLim = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(10002).get();
        System.out.println("Antonio Lim's hourly rate is " + antonioLim.hourlyRate.toString());
    }
}
