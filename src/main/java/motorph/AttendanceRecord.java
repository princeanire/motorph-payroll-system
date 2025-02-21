package motorph;

import java.time.LocalDate;
import java.time.LocalTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

public class AttendanceRecord {
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
}