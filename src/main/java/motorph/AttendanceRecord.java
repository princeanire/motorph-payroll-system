package motorph;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

/**
 * Represents an attendance record for an employee in the MotorPH payroll system.
 * This class stores information about an employee's attendance including their
 * identification, name, date, and log in/out times.
 * <p>
 * AttendanceRecord objects are typically created by parsing CSV attendance data
 * using the OpenCSV library's annotations for data binding. The class provides
 * functionality to check for overtime hours and retrieve the month of the record.
 * <p>
 * The class uses custom converters for date and time fields to properly parse
 * the CSV input data into Java time objects.
 * 
 * @see motorph.Converter.DateConverter
 * @see motorph.Converter.TimeConverter
 * @see motorph.EmployeeDatabaseService
 */
public class AttendanceRecord {

    // Define constants for performance optimization
    private static final LocalTime OVERTIME_CUTOFF = LocalTime.of(8, 11);
    
    @CsvBindByName(column = "Employee #", required = true)
    public int employeeId;

    @CsvBindByName(column = "First Name", required = true)
    public String firstName;

    @CsvBindByName(column = "Last Name", required = true)
    public String lastName;

    @CsvCustomBindByName(column = "Date", required = true, converter = Converter.DateConverter.class)
    public LocalDate date;

    @CsvCustomBindByName(column = "Log In", required = true, converter = Converter.TimeConverter.class)
    public LocalTime logIn;

    @CsvCustomBindByName(column = "Log Out", required = true, converter = Converter.TimeConverter.class)
    public LocalTime logOut;

    /**
     * Determines if the employee has worked overtime hours on this day.
     * Overtime is only considered valid when the employee arrived on time
     * (before 8:10 AM) and the total working hours exceed 8 hours.
     *
     * @return true if the employee has valid overtime hours, false otherwise
     */
    public boolean hasOvertimeHours() {
  // Employees who arrive late (after 8:10 AM) are not eligible for overtime
        return !logIn.isAfter(OVERTIME_CUTOFF) && 
               EmployeeDatabaseService.calculateEmployeeWorkingHours(logIn, logOut) > 8;
    }

    /**
     * Gets the month of the attendance record's date.
     * 
     * @return The month as a {@link java.time.Month} enum value.
     */
    public Month getMonth() {
        return this.date.getMonth();
    }

    /**
     * Default constructor for the AttendanceRecord class.
     * Initializes a new instance of AttendanceRecord without setting any initial values.
     */
    public AttendanceRecord() {

    }

    /**
     * Constructs a new AttendanceRecord instance.
     * 
     * @param employeeId The unique identifier for the employee
     * @param firstName  The first name of the employee
     * @param lastName   The last name of the employee
     * @param date       The date of the attendance record
     * @param logIn      The time when the employee logged in
     * @param logOut     The time when the employee logged out
     */
    public AttendanceRecord(int employeeId, String firstName, String lastName, LocalDate date, LocalTime logIn,
            LocalTime logOut) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.logIn = logIn;
        this.logOut = logOut;
    }
}
