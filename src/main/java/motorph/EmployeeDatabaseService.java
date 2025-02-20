package motorph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;

public class EmployeeDatabaseService {
    public static List<AttendanceRecord> attendanceRecords;

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
            System.out.println("Employee Database CSV file not found: " + fileNotFoundException);
        }
    }

    public static List<AttendanceRecord> getAttendanceRecordsByEmployeeId(int employeeId) {
        return getAttendanceRecords().stream()
                .filter(record -> record.employeeId == employeeId)
                .collect(Collectors.toList());
    }

}
