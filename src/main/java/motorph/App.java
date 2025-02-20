package motorph;

import java.util.List;

public class App {
    public static void main(String[] args) {
        EmployeeDatabaseService.getAttendanceRecords();
        List<AttendanceRecord> antonioLimAttendanceRecords = EmployeeDatabaseService
                .getAttendanceRecordsByEmployeeId(10002);
        for (AttendanceRecord attendanceRecord : antonioLimAttendanceRecords) {
            System.out.println(attendanceRecord.employeeId);
        }
    }
}
