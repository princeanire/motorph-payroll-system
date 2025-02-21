package motorph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;

public class EmployeeDatabaseService {
    public static List<AttendanceRecord> attendanceRecords;
    public static List<Employee> employees;

    public static List<AttendanceRecord> getAttendanceRecords() {
        if (attendanceRecords == null) {
            loadAttendanceRecords();
        }
        return attendanceRecords;
    }

    private static void loadAttendanceRecords() {
        try {
            attendanceRecords = new CsvToBeanBuilder<AttendanceRecord>(
                    new FileReader("src/main/resources/attendance-record.csv"))
                    .withType(AttendanceRecord.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Attendance Records Database CSV file not found: " + fileNotFoundException);
        }
    }

    public static List<AttendanceRecord> getAttendanceRecordsByEmployeeId(int employeeId) {
        return getAttendanceRecords().stream()
                .filter(record -> record.employeeId == employeeId)
                .collect(Collectors.toList());
    }

    private static void loadEmployeeDetails() {
        try {
            employees = new CsvToBeanBuilder<Employee>(
                    new FileReader("src/main/resources/employee-details.csv"))
                    .withType(Employee.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Employee Details Database CSV file not found: " + fileNotFoundException);
        }
    }

    public static List<Employee> getEmployeeDetails() {
        if (employees == null) {
            loadEmployeeDetails();
        }
        return employees;
    }

    public static Optional<Employee> getEmployeeDetailsByEmployeeId(int employeeId) {
        return getEmployeeDetails().stream()
                .filter(employeeDetails -> employeeDetails.employeeId == employeeId).findFirst();
    }

}
