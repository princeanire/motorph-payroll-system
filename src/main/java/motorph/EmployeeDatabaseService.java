package motorph;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDatabaseService {
    private static List<AttendanceRecord> attendanceRecords;
    private static List<Employee> employees;

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
        } catch (FileNotFoundException e) {
            System.out.println("Attendance Records Database CSV file not found: " + e.getMessage());
        }
    }

    public static List<AttendanceRecord> getAttendanceRecordsByEmployeeId(int employeeId) {
        return getAllAttendanceRecords().stream()
                .filter(record -> record.employeeId == employeeId)
                .collect(Collectors.toList());
    }

    public static Map<Month, List<AttendanceRecord>> getAttendanceRecordsByMonth(int employeeId) {
        return getAttendanceRecordsByEmployeeId(employeeId).stream()
                .collect(Collectors.groupingBy(record -> record.date.getMonth()));
    }

    public static Map<Integer, List<AttendanceRecord>> getAttendanceRecordsByWeek(int employeeId, Month month) {
        List<AttendanceRecord> monthRecords = getAttendanceRecordsByMonth(employeeId).get(month);
        if (monthRecords == null) {
            return new HashMap<>();
        }

        WeekFields weekFields = WeekFields.of(Locale.US);
        return monthRecords.stream()
                .collect(Collectors.groupingBy(record -> record.date.get(weekFields.weekOfMonth())));
    }

    private static void loadEmployeeDetails() {
        try {
            employees = new CsvToBeanBuilder<Employee>(
                    new FileReader("src/main/resources/employee-details.csv"))
                    .withType(Employee.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            System.out.println("Employee Details Database CSV file not found: " + e.getMessage());
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
                .filter(employee -> employee.employeeId == employeeId)
                .findFirst();
    }

    public static int calculateEmployeeWorkingHours(LocalTime logInTime, LocalTime logOutTime) {
        return (int) Duration.between(logInTime, logOutTime).minusHours(1).toHours();
    }

    public static int calculateEmployeeWorkingHours(AttendanceRecord record) {
        return calculateEmployeeWorkingHours(record.logIn, record.logOut);
    }

    public static double calculateEmployeeWorkingDuration(LocalTime logInTime, LocalTime logOutTime) {
        return Duration.between(logInTime, logOutTime).minusHours(1).toMinutes() / 60.0;
    }

    public static BigDecimal calculateEmployeeDailyBasicPay(int employeeId, double workingDuration) {
        return getEmployeeDetailsByEmployeeId(employeeId)
                .map(employee -> employee.hourlyRate.multiply(BigDecimal.valueOf(workingDuration)))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal calculateEmployeeDeminimisBenefits(int employeeId) {
        return getEmployeeDetailsByEmployeeId(employeeId)
                .map(employee -> employee.clothingAllowance
                        .add(employee.riceSubsidy)
                        .add(employee.phoneAllowance))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal calculateEmployeeOvertimePay(AttendanceRecord record) {
        return getEmployeeDetailsByEmployeeId(record.employeeId)
                .filter(employee -> record.hasOvertimeHours())
                .map(employee -> {
                    int overtimeHours = calculateEmployeeWorkingHours(record) - 8;
                    return employee.hourlyRate.multiply(BigDecimal.valueOf(overtimeHours).multiply(BigDecimal.valueOf(1.25)));
                })
                .orElse(BigDecimal.ZERO);
    }

    public static void calculateEmployeeWeeklySalary(int employeeId) {
        getEmployeeDetailsByEmployeeId(employeeId).ifPresent(employee -> {
            for (Month month : Month.values()) {
                calculateEmployeeWeeklySalary(employee, getAttendanceRecordsByWeek(employeeId, month));
            }
        });
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    public static void calculateEmployeeWeeklySalary(Employee employee, Map<Integer, List<AttendanceRecord>> weeklyRecords) {
        System.out.println(employee.employeeId + ", " + employee.firstName + " " + employee.lastName + ", " + employee.birthday);

        for (var entry : weeklyRecords.entrySet()) {
            int weekNumber = entry.getKey();
            List<AttendanceRecord> weekAttendance = entry.getValue();

            double totalWorkingDuration = weekAttendance.stream()
                    .mapToDouble(record -> calculateEmployeeWorkingDuration(record.logIn, record.logOut))
                    .sum();
            BigDecimal totalOvertimePay = weekAttendance.stream()
                    .map(EmployeeDatabaseService::calculateEmployeeOvertimePay)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal grossSalary = calculateEmployeeDailyBasicPay(employee.employeeId, totalWorkingDuration)
                    .add(totalOvertimePay);

            System.out.println("Week " + weekNumber + " Hours: " + totalWorkingDuration);
            System.out.println("Gross Salary for Week " + weekNumber + ": " + grossSalary);

            if (weekNumber == Collections.max(weeklyRecords.keySet()) - 1) {
                BigDecimal deductions = SalaryDeductionService.calculateSocialSecuritySystemContribution(employee.basicSalary)
                        .add(SalaryDeductionService.calculateWithholdingTax(employee.basicSalary))
                        .add(SalaryDeductionService.calculatePhilhealthContribution(employee.basicSalary))
                        .add(SalaryDeductionService.calculatePagIbigContribution(employee.basicSalary));
                BigDecimal netSalary = grossSalary.subtract(deductions).add(calculateEmployeeDeminimisBenefits(employee.employeeId));

                System.out.println("Net Weekly Salary for Week " + weekNumber + ": " + netSalary);
            }
        }
    }
}
