package motorph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.*;
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

    public static Map<Month, List<AttendanceRecord>> getAttendanceRecordsByMonth(int employeeId) {
        Map<Month, List<AttendanceRecord>> map = new HashMap<>();
        for (AttendanceRecord record : getAttendanceRecordsByEmployeeId(employeeId)) {
            map.computeIfAbsent(record.date.getMonth(), k -> new ArrayList<>()).add(record);
        }
        return map;
    }

    public static Map<Integer, List<AttendanceRecord>> getAttendanceRecordsByWeek(int employeeId, Month month) {
        // Get the attendance records for the specified month
        List<AttendanceRecord> monthRecords = getAttendanceRecordsByMonth(employeeId).get(month);

        if (monthRecords == null) {
            return new HashMap<>(); // Return empty map if no records for this month
        }

        // Group records by week of month
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
        return shiftSchedule.toHoursPart() - lunchBreak.toHoursPart();
    }

    public static int calculateEmployeeWorkingHours(AttendanceRecord attendanceRecord) {
        Duration shiftSchedule = Duration.between(attendanceRecord.logIn, attendanceRecord.logOut);
        Duration lunchBreak = Duration.ofHours(1);
        return shiftSchedule.toHoursPart() - lunchBreak.toHoursPart();
    }

    public static double calculateEmployeeWorkingDuration(LocalTime logInTime, LocalTime logOutTime) {
        Duration shiftSchedule = Duration.between(logInTime, logOutTime);
        Duration lunchBreak = Duration.ofHours(1);
        long totalMinutes = shiftSchedule.toMinutes() - lunchBreak.toMinutes();
        return totalMinutes / 60.0;
    }

    public static BigDecimal calculateEmployeeDailyBasicPay(int employeeId, double employeeWorkingDuration) {
        BigDecimal hourlyRate = getEmployeeDetailsByEmployeeId(employeeId).get().hourlyRate;
        return hourlyRate.multiply(BigDecimal.valueOf(employeeWorkingDuration));
    }

    public static BigDecimal calculateEmployeeDailyBasicPay(int employeeId, int employeeWorkingHours) {
        BigDecimal hourlyRate = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().hourlyRate;
        return hourlyRate.multiply(BigDecimal.valueOf(employeeWorkingHours));
    }

    public static BigDecimal calculateEmployeeDeminimisBenefits(int employeeId) {
        System.out.println("Clothing Allowance  is " + EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().clothingAllowance);
        System.out.println("Rice Subsidy is " + EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().riceSubsidy);
        BigDecimal clothingAllowance = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().clothingAllowance;
        BigDecimal riceSubsidy = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().riceSubsidy;
        System.out.println("Phone Allowance is " + EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().phoneAllowance);
        BigDecimal phoneAllowance = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().phoneAllowance;

        return clothingAllowance.add(riceSubsidy).add(phoneAllowance);
    }

    public static BigDecimal calculateEmployeeOvertimePay(AttendanceRecord attendanceRecord) {
        BigDecimal hourlyRate = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(attendanceRecord.employeeId)
                .get().hourlyRate;

        if (attendanceRecord.hasOvertimeHours()) {
            int workingHours = EmployeeDatabaseService.calculateEmployeeWorkingHours(attendanceRecord.logIn,
                    attendanceRecord.logOut);
            int overtimeHours = workingHours - 8;
            BigDecimal overtimeRate = BigDecimal.valueOf(1.25);
            return hourlyRate.multiply(BigDecimal.valueOf(overtimeHours).multiply(overtimeRate)).stripTrailingZeros();

        } else {
            return BigDecimal.valueOf(0.0);
        }
    }

    public static void calculateEmployeeWeeklySalary(int employeeId) {

        Employee employee = getEmployeeDetailsByEmployeeId(employeeId).get();
        Map<Integer, List<AttendanceRecord>> juneWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.JUNE);
        Map<Integer, List<AttendanceRecord>> julyWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.JULY);
        Map<Integer, List<AttendanceRecord>> augustWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.AUGUST);
        Map<Integer, List<AttendanceRecord>> septemberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.SEPTEMBER);
        Map<Integer, List<AttendanceRecord>> octoberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.OCTOBER);
        Map<Integer, List<AttendanceRecord>> novemberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.NOVEMBER);
        Map<Integer, List<AttendanceRecord>> decemberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId, Month.DECEMBER);

        calculateEmployeeWeeklySalary(employee, juneWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, julyWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, augustWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, septemberWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, octoberWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, novemberWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, decemberWeeklyAttendanceRecords);
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    public static void calculateEmployeeWeeklySalary(Employee employee, Map<Integer, List<AttendanceRecord>> weeklyAttendanceRecords) {
        System.out.println(employee.employeeId + ", " + employee.firstName + " " + employee.lastName + ", " + employee.birthday);
        Integer lastWeekNumber = Collections.max(weeklyAttendanceRecords.keySet()) - 1;

        for (Integer weekNumber : weeklyAttendanceRecords.keySet()) {
            BigDecimal employeeGrossWeeklySalary = BigDecimal.ZERO;
            double employeeWorkingDuration = 0.0;
            List<AttendanceRecord> weekRecords = weeklyAttendanceRecords.get(weekNumber);
            for (AttendanceRecord record : weekRecords) {
                employeeWorkingDuration += calculateEmployeeWorkingDuration(record.logIn, record.logOut);
                employeeGrossWeeklySalary = employeeGrossWeeklySalary.add(calculateEmployeeOvertimePay(record));
            }
            employeeGrossWeeklySalary = employeeGrossWeeklySalary.add(calculateEmployeeDailyBasicPay(employee.employeeId, employeeWorkingDuration));
            System.out.println("Number of hours for Week " + weekNumber + " of " + weeklyAttendanceRecords.get(2).getFirst().getMonth() + " 2024 " + ": " + employeeWorkingDuration);
            System.out.println("Gross Weekly Salary " + "for Week " + weekNumber + " of " + weeklyAttendanceRecords.get(2).getFirst().getMonth() + " 2024 " + ": " + employeeGrossWeeklySalary);
            if (weekNumber.equals(lastWeekNumber)) {
                System.out.println("Social Security Contribution deduction is: " + SalaryDeductionService.calculateSocialSecuritySystemContribution(employee.basicSalary));
                employeeGrossWeeklySalary = employeeGrossWeeklySalary.subtract(SalaryDeductionService.calculateSocialSecuritySystemContribution(employee.basicSalary));
                System.out.println("Withholding Tax deduction is: " + SalaryDeductionService.calculateWithholdingTax(employee.basicSalary));
                employeeGrossWeeklySalary = employeeGrossWeeklySalary.subtract(SalaryDeductionService.calculateWithholdingTax(employee.basicSalary));
                System.out.println("Philhealth Contribution deduction is: " + SalaryDeductionService.calculatePhilhealthContribution(employee.basicSalary));
                employeeGrossWeeklySalary = employeeGrossWeeklySalary.subtract(SalaryDeductionService.calculatePhilhealthContribution(employee.basicSalary));
                System.out.println("Pag-Ibig Contribution deduction is: " + SalaryDeductionService.calculatePagIbigContribution(employee.basicSalary));
                BigDecimal employeeNetWeeklySalary = employeeGrossWeeklySalary.subtract(SalaryDeductionService.calculatePagIbigContribution(employee.basicSalary));
                employeeNetWeeklySalary = employeeNetWeeklySalary.add(calculateEmployeeDeminimisBenefits(employee.employeeId));
                System.out.println("Net Weekly Salary " + "in Week " + weekNumber + " of " + weeklyAttendanceRecords.get(2).getFirst().getMonth() + " 2024 " + ": " + employeeNetWeeklySalary);
            }
        }

    }
}
