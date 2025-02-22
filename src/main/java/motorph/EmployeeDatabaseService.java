package motorph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;

public class EmployeeDatabaseService {
    public static List<AttendanceRecord> attendanceRecords;
    public static List<Employee> employees;

    public static List<AttendanceRecord> getAllAttendanceRecords() {
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
        return getAllAttendanceRecords().stream()
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

    public static List<Employee> getAllEmployeeDetails() {
        if (employees == null) {
            loadEmployeeDetails();
        }
        return employees;
    }

    public static Optional<Employee> getEmployeeDetailsByEmployeeId(int employeeId) {
        return getAllEmployeeDetails().stream()
                .filter(employeeDetails -> employeeDetails.employeeId == employeeId).findFirst();
    }

    public static int calculateEmployeeWorkingHours(LocalTime logInTime, LocalTime logOutTime) {
        Duration shiftSchedule = Duration.between(logInTime, logOutTime);
        Duration lunchBreak = Duration.ofHours(1);
        int employeeWorkingHours = shiftSchedule.toHoursPart() - lunchBreak.toHoursPart();
        return employeeWorkingHours;
    }

    public static int calculateEmployeeWorkingHours(AttendanceRecord attendanceRecord) {
        Duration shiftSchedule = Duration.between(attendanceRecord.logIn, attendanceRecord.logOut);
        Duration lunchBreak = Duration.ofHours(1);
        int employeeWorkingHours = shiftSchedule.toHoursPart() - lunchBreak.toHoursPart();
        return employeeWorkingHours;
    }

    public static BigDecimal calculateEmployeeDailyBasicPay(int employeeId, int employeeWorkingHours) {
        BigDecimal hourlyRate = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().hourlyRate;
        BigDecimal employeeBasicPay = hourlyRate.multiply(BigDecimal.valueOf(employeeWorkingHours)).setScale(2,
                RoundingMode.UNNECESSARY);
        return employeeBasicPay;
    }

    public static BigDecimal calculateEmployeeDeminimisBenefits(int employeeId) {
        BigDecimal clothingAllowance = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().clothingAllowance;
        BigDecimal riceSubsidy = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().riceSubsidy;
        BigDecimal phoneAllowance = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().phoneAllowance;

        return clothingAllowance.add(riceSubsidy).add(phoneAllowance).setScale(2, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal calculateEmployeeOvertimePay(AttendanceRecord attendanceRecord) {
        BigDecimal hourlyRate = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(attendanceRecord.employeeId)
                .get().hourlyRate;

        if (attendanceRecord.hasOvertimeHours()) {
            int workingHours = EmployeeDatabaseService.calculateEmployeeWorkingHours(attendanceRecord.logIn,
                    attendanceRecord.logOut);
            int overtimeHours = workingHours - 8;
            BigDecimal overtimeRate = BigDecimal.valueOf(1.25);
            BigDecimal overtimePay = hourlyRate.multiply(BigDecimal.valueOf(overtimeHours).multiply(overtimeRate));
            return overtimePay.setScale(2, RoundingMode.UP);

        } else {
            return BigDecimal.valueOf(0.0);
        }
    }

}
