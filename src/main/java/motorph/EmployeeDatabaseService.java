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

/**
 * The EmployeeDatabaseService class serves as the central hub for managing and
 * processing
 * employee data and attendance records for the payroll system. It provides
 * static methods to:
 * 
 * - Load and retrieve employee details and attendance records from CSV files.
 * - Filter attendance records by employee ID, month, or week.
 * - Calculate various metrics including working hours, working duration, daily
 * basic pay,
 * overtime pay, and weekly salary.
 * - Compute payroll deductions such as Social Security System contribution,
 * Withholding Tax,
 * Philhealth contribution, and Pag-Ibig contribution.
 * - Aggregate de minimis benefits like clothing allowance, rice subsidy, and
 * phone allowance.
 * 
 * The class leverages the CsvToBeanBuilder for CSV parsing and utilizes Java
 * Streams for efficient
 * data processing. All data is maintained in static fields, providing a shared
 * state for the application.
 *
 * @see AttendanceRecord
 * @see Employee
 * @see CsvToBeanBuilder
 * @see SalaryDeductionService
 */
public class EmployeeDatabaseService {
    /**
     * Holds the list of attendance records for employees.
     *
     * <p>
     * This static field is used to store all employee attendance records, providing
     * a centralized
     * collection that can be accessed throughout the application. Each entry in the
     * list represents
     * an individual attendance record for an employee.
     */
    public static List<AttendanceRecord> attendanceRecords;
    public static List<Employee> employees;

    /**
     * Retrieves the list of all attendance records.
     * <p>
     * This method checks if the attendance records have been loaded. If they have
     * not been loaded yet,
     * it invokes the {@code loadAttendanceRecords()} method to load them before
     * returning the list.
     * </p>
     *
     * @return a {@code List} containing all attendance records.
     */

    public static List<AttendanceRecord> getAllAttendanceRecords() {
        if (attendanceRecords == null) {
            loadAttendanceRecords();
        }
        return attendanceRecords;
    }

    /**
     * Loads the attendance records from the CSV file located at
     * "src/main/resources/attendance-record.csv".
     * <p>
     * This method utilizes the CsvToBeanBuilder to read and parse the CSV file into
     * a list of
     * {@code AttendanceRecord} objects. If the CSV file is not found, a
     * {@code FileNotFoundException}
     * is caught and an appropriate error message is printed.
     * </p>
     *
     * @see CsvToBeanBuilder
     * @see AttendanceRecord
     */
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

    /**
     * Retrieves attendance records for a specific employee.
     * 
     * This method filters the attendance records from the database to only include
     * those belonging to the specified employee ID.
     *
     * @param employeeId The unique identifier of the employee
     * @return A list of AttendanceRecord objects associated with the employee
     */
    public static List<AttendanceRecord> getAttendanceRecordsByEmployeeId(int employeeId) {
        return getAllAttendanceRecords().stream()
                .filter(record -> record.employeeId == employeeId)
                .collect(Collectors.toList());
    }

    /**
     * Groups attendance records of a specific employee by month.
     * 
     * This method retrieves all attendance records for the given employee ID and
     * organizes them into a map where each key represents a month and the value
     * is a list of attendance records occurring in that month.
     *
     * @param employeeId The unique identifier of the employee whose attendance
     *                   records are being grouped
     * @return A map with Month as the key and a list of AttendanceRecord objects as
     *         the value
     */
    public static Map<Month, List<AttendanceRecord>> getAttendanceRecordsByMonth(int employeeId) {
        Map<Month, List<AttendanceRecord>> map = new HashMap<>();
        for (AttendanceRecord record : getAttendanceRecordsByEmployeeId(employeeId)) {
            map.computeIfAbsent(record.date.getMonth(), k -> new ArrayList<>()).add(record);
        }
        return map;
    }

    /**
     * Retrieves attendance records for a specific employee, grouped by week for a
     * given month.
     * 
     * This method first retrieves all attendance records for the employee for the
     * specified month,
     * then organizes them into a map where the key is the week number within the
     * month (1-based),
     * and the value is a list of attendance records for that week.
     * 
     * @param employeeId The unique identifier for the employee whose attendance
     *                   records are being retrieved
     * @param month      The month for which to retrieve attendance records
     * @return A Map with week numbers as keys and lists of AttendanceRecord objects
     *         as values.
     *         Returns an empty map if no records exist for the specified month.
     */
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

    /**
     * Loads employee details from a CSV file into the employees list.
     * 
     * This method reads the employee-details.csv file from the resources directory
     * and parses it into a list of Employee objects using the CsvToBean builder.
     * If the file is not found, an error message is printed to the console.
     * 
     * The CSV file should match the structure of the Employee class fields.
     * 
     * @throws FileNotFoundException if the employee-details.csv file cannot be
     *                               found
     */
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

    /**
     * Retrieves a list of all employee details from the database.
     * 
     * If the employees list hasn't been loaded yet, this method will call
     * loadEmployeeDetails() to populate the list before returning it.
     * 
     * @return List<Employee> A list containing all employee records
     */
    public static List<Employee> getAllEmployeeDetails() {
        if (employees == null) {
            loadEmployeeDetails();
        }
        return employees;
    }

    /**
     * Retrieves an employee by their unique employee ID.
     * 
     * @param employeeId The unique identifier for the employee
     * @return An Optional containing the Employee if found, or an empty Optional if
     *         no employee with the given ID exists
     */
    public static Optional<Employee> getEmployeeDetailsByEmployeeId(int employeeId) {
        return getAllEmployeeDetails().stream()
                .filter(employeeDetails -> employeeDetails.employeeId == employeeId).findFirst();
    }

    /**
     * Calculates the total working hours for an employee based on log in and log
     * out times.
     * The method subtracts a one-hour lunch break from the total shift duration.
     *
     * @param logInTime  the time when the employee logged in
     * @param logOutTime the time when the employee logged out
     * @return the number of working hours excluding lunch break
     */
    public static int calculateEmployeeWorkingHours(LocalTime logInTime, LocalTime logOutTime) {
        Duration shiftSchedule = Duration.between(logInTime, logOutTime);
        Duration lunchBreak = Duration.ofHours(1);
        return shiftSchedule.toHoursPart() - lunchBreak.toHoursPart();
    }

    /**
     * Calculates the total working hours for an employee based on their attendance
     * record.
     * This method takes into account the employee's log in and log out times, and
     * subtracts
     * a standard 1-hour lunch break from the total shift duration.
     *
     * @param attendanceRecord the attendance record containing the employee's log
     *                         in and log out times
     * @return the total number of working hours, excluding lunch break
     */
    public static int calculateEmployeeWorkingHours(AttendanceRecord attendanceRecord) {
        Duration shiftSchedule = Duration.between(attendanceRecord.logIn, attendanceRecord.logOut);
        Duration lunchBreak = Duration.ofHours(1);
        return shiftSchedule.toHoursPart() - lunchBreak.toHoursPart();
    }

    /**
     * Calculates the working duration of an employee in hours.
     * 
     * This method takes the log-in and log-out times of an employee and calculates
     * the total working hours, excluding a one-hour lunch break.
     *
     * @param logInTime  The time when the employee logged in
     * @param logOutTime The time when the employee logged out
     * @return The total working duration in hours (as a decimal value)
     */
    public static double calculateEmployeeWorkingDuration(LocalTime logInTime, LocalTime logOutTime) {
        Duration shiftSchedule = Duration.between(logInTime, logOutTime);
        Duration lunchBreak = Duration.ofHours(1);
        long totalMinutes = shiftSchedule.toMinutes() - lunchBreak.toMinutes();
        return totalMinutes / 60.0;
    }

    /**
     * Calculates the daily basic pay for an employee based on their hourly rate and
     * working duration.
     *
     * @param employeeId              The unique identifier of the employee
     * @param employeeWorkingDuration The working duration consisting of hours and
     *                                minutes the employee worked in a day
     * @return The calculated daily basic pay as a BigDecimal
     * @throws NoSuchElementException if no employee is found with the given
     *                                employeeId
     */
    public static BigDecimal calculateEmployeeDailyBasicPay(int employeeId, double employeeWorkingDuration) {
        BigDecimal hourlyRate = getEmployeeDetailsByEmployeeId(employeeId).get().hourlyRate;
        return hourlyRate.multiply(BigDecimal.valueOf(employeeWorkingDuration));
    }

    /**
     * Calculates the daily basic pay for an employee based on their hourly rate and
     * working hours.
     *
     * @param employeeId           The unique identifier of the employee
     * @param employeeWorkingHours The number of hours the employee worked in a day
     * @return The calculated daily basic pay as a BigDecimal
     * @throws NoSuchElementException if the employee with the given ID is not found
     */
    public static BigDecimal calculateEmployeeDailyBasicPay(int employeeId, int employeeWorkingHours) {
        BigDecimal hourlyRate = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().hourlyRate;
        return hourlyRate.multiply(BigDecimal.valueOf(employeeWorkingHours));
    }

    /**
     * Calculates the total De Minimis Benefits for a specific employee.
     * 
     * This method retrieves the clothing allowance, rice subsidy, and phone
     * allowance
     * for an employee identified by their employee ID, then calculates and returns
     * the sum of these benefits.
     * 
     * Note: This method prints the individual benefit amounts to the console for
     * debugging purposes.
     * 
     * @param employeeId The unique identifier of the employee whose benefits are
     *                   being calculated
     * @return The sum of the employee's clothing allowance, rice subsidy, and phone
     *         allowance as a BigDecimal
     * @throws NoSuchElementException If no employee with the given ID exists
     */
    public static BigDecimal calculateEmployeeDeminimisBenefits(int employeeId) {
        System.out.println("Clothing Allowance  is "
                + EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().clothingAllowance);
        System.out.println("Rice Subsidy is "
                + EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().riceSubsidy);
        BigDecimal clothingAllowance = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().clothingAllowance;
        BigDecimal riceSubsidy = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().riceSubsidy;
        System.out.println("Phone Allowance is "
                + EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId).get().phoneAllowance);
        BigDecimal phoneAllowance = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(employeeId)
                .get().phoneAllowance;

        return clothingAllowance.add(riceSubsidy).add(phoneAllowance);
    }

    /**
     * Calculates the overtime pay for an employee based on their attendance record.
     * 
     * @param attendanceRecord The attendance record containing employee ID, log-in
     *                         and log-out times
     * @return The calculated overtime pay amount. Returns 0 if there are no
     *         overtime hours.
     *         Overtime pay is calculated as hourly rate * overtime hours * 1.25
     *         (overtime rate).
     *         Overtime hours are defined as any hours worked beyond the standard
     *         8-hour workday.
     *         Employee is eligible for overtime pay if they login before 8:10 AM
     *         and work more than 8 hours.
     */
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

    /**
     * Calculates weekly salary for an employee for multiple months.
     * 
     * This method retrieves attendance records for an employee from June to
     * December,
     * organized by week, and calculates the weekly salary for each week in these
     * months.
     * 
     * @param employeeId The unique identifier of the employee for whom to calculate
     *                   weekly salary
     * @throws NoSuchElementException If no employee is found with the given ID
     * @see #getEmployeeDetailsByEmployeeId(int)
     * @see #getAttendanceRecordsByWeek(int, Month)
     * @see #calculateEmployeeWeeklySalary(Employee, Map)
     */
    public static void calculateEmployeeWeeklySalary(int employeeId) {

        Employee employee = getEmployeeDetailsByEmployeeId(employeeId).get();
        Map<Integer, List<AttendanceRecord>> juneWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.JUNE);
        Map<Integer, List<AttendanceRecord>> julyWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.JULY);
        Map<Integer, List<AttendanceRecord>> augustWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.AUGUST);
        Map<Integer, List<AttendanceRecord>> septemberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.SEPTEMBER);
        Map<Integer, List<AttendanceRecord>> octoberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.OCTOBER);
        Map<Integer, List<AttendanceRecord>> novemberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.NOVEMBER);
        Map<Integer, List<AttendanceRecord>> decemberWeeklyAttendanceRecords = getAttendanceRecordsByWeek(employeeId,
                Month.DECEMBER);

        calculateEmployeeWeeklySalary(employee, juneWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, julyWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, augustWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, septemberWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, octoberWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, novemberWeeklyAttendanceRecords);
        calculateEmployeeWeeklySalary(employee, decemberWeeklyAttendanceRecords);
        System.out.println(
                "--------------------------------------------------------------------------------------------------");
    }

    /**
     * Calculates the weekly salary for an employee based on attendance records.
     * 
     * This method processes each week's attendance records to calculate:
     * - Working duration for each week
     * - Gross weekly salary (basic pay + overtime)
     * - For the last week of the period, it also calculates and applies deductions:
     * - Social Security System contribution
     * - Withholding Tax
     * - Philhealth contribution
     * - Pag-Ibig contribution
     * - For the last week, it also adds de minimis benefits to calculate the net
     * weekly salary
     * 
     * The method prints detailed information about all calculations to the console.
     * 
     * @param employee                The employee for whom to calculate the weekly
     *                                salary
     * @param weeklyAttendanceRecords Map of week numbers to their corresponding
     *                                attendance records
     */
    public static void calculateEmployeeWeeklySalary(Employee employee,
            Map<Integer, List<AttendanceRecord>> weeklyAttendanceRecords) {
        System.out.println(
                employee.employeeId + ", " + employee.firstName + " " + employee.lastName + ", " + employee.birthday);
        Integer lastWeekNumber = Collections.max(weeklyAttendanceRecords.keySet()) - 1;

        for (Integer weekNumber : weeklyAttendanceRecords.keySet()) {
            BigDecimal employeeGrossWeeklySalary = BigDecimal.ZERO;
            double employeeWorkingDuration = 0.0;
            List<AttendanceRecord> weekRecords = weeklyAttendanceRecords.get(weekNumber);
            for (AttendanceRecord record : weekRecords) {
                employeeWorkingDuration += calculateEmployeeWorkingDuration(record.logIn, record.logOut);
                employeeGrossWeeklySalary = employeeGrossWeeklySalary.add(calculateEmployeeOvertimePay(record));
            }
            employeeGrossWeeklySalary = employeeGrossWeeklySalary
                    .add(calculateEmployeeDailyBasicPay(employee.employeeId, employeeWorkingDuration));
            System.out.println("Number of hours for Week " + weekNumber + " of "
                    + weeklyAttendanceRecords.get(2).getFirst().getMonth() + " 2024 " + ": " + employeeWorkingDuration);
            System.out.println("Gross Weekly Salary " + "for Week " + weekNumber + " of "
                    + weeklyAttendanceRecords.get(2).getFirst().getMonth() + " 2024 " + ": "
                    + employeeGrossWeeklySalary);
            if (weekNumber.equals(lastWeekNumber)) {
                System.out.println("Social Security Contribution deduction is: "
                        + SalaryDeductionService.calculateSocialSecuritySystemContribution(employee.basicSalary));
                employeeGrossWeeklySalary = employeeGrossWeeklySalary.subtract(
                        SalaryDeductionService.calculateSocialSecuritySystemContribution(employee.basicSalary));
                System.out.println("Withholding Tax deduction is: "
                        + SalaryDeductionService.calculateWithholdingTax(employee.basicSalary));
                employeeGrossWeeklySalary = employeeGrossWeeklySalary
                        .subtract(SalaryDeductionService.calculateWithholdingTax(employee.basicSalary));
                System.out.println("Philhealth Contribution deduction is: "
                        + SalaryDeductionService.calculatePhilhealthContribution(employee.basicSalary));
                employeeGrossWeeklySalary = employeeGrossWeeklySalary
                        .subtract(SalaryDeductionService.calculatePhilhealthContribution(employee.basicSalary));
                System.out.println("Pag-Ibig Contribution deduction is: "
                        + SalaryDeductionService.calculatePagIbigContribution(employee.basicSalary));
                BigDecimal employeeNetWeeklySalary = employeeGrossWeeklySalary
                        .subtract(SalaryDeductionService.calculatePagIbigContribution(employee.basicSalary));
                employeeNetWeeklySalary = employeeNetWeeklySalary
                        .add(calculateEmployeeDeminimisBenefits(employee.employeeId));
                System.out.println("Net Weekly Salary " + "in Week " + weekNumber + " of "
                        + weeklyAttendanceRecords.get(2).getFirst().getMonth() + " 2024 " + ": "
                        + employeeNetWeeklySalary);
            }
        }
    }
}
