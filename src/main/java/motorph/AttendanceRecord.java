package motorph;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

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

    public boolean hasOvertimeHours() {
        return !logIn.isAfter(OVERTIME_CUTOFF) && 
               EmployeeDatabaseService.calculateEmployeeWorkingHours(logIn, logOut) > 8
    }

    public Month getMonth() {
        return this.date.getMonth();
    }

    public AttendanceRecord() {

    }

    public AttendanceRecord(int employeeId, String firstName, String lastName, LocalDate date, LocalTime logIn, LocalTime logOut)
    {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.logIn = logIn;
        this.logOut = logOut;
    }
}
