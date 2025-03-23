package motorph;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

/**
 * Represents an employee in the MotorPH Payroll System.
 * This class contains all the personal and employment information of an employee
 * including identification numbers, contact details, employment status, compensation,
 * and benefits information.
 * 
 * The class uses OpenCSV annotations for binding CSV data to object properties,
 * with custom converters for specific data types like dates and decimal values.
 * 
 * All fields are public to facilitate direct access, but care should be taken
 * when modifying these fields directly.
 */
public class Employee {
    @CsvBindByName(column = "Employee #", required = true)
    public int employeeId;
    @CsvBindByName(column = "Last Name", required = true)
    public String lastName;
    @CsvBindByName(column = "First Name", required = true)
    public String firstName;
    @CsvCustomBindByName(column = "Birthday", required = true, converter = Converter.DateConverter.class)
    public LocalDate birthday;
    @CsvBindByName(column = "Address", required = true)
    public String address;
    @CsvBindByName(column = "Phone Number", required = true)
    public String phoneNumber;
    @CsvBindByName(column = "SSS #", required = true)
    public String socialSecurityId;
    @CsvBindByName(column = "Philhealth #", required = true)
    public String philhealthId;
    @CsvBindByName(column = "TIN #", required = true)
    public String taxpayerId;
    @CsvBindByName(column = "Pag-ibig #", required = true)
    public String pagIbigId;
    @CsvCustomBindByName(column = "Status", required = true, converter = Converter.EmploymentStatusConverter.class)
    public EmploymentStatus employmentStatus;
    @CsvBindByName(column = "position", required = true)
    public String position;
    @CsvBindByName(column = "Immediate Supervisor", required = true)
    public String immediateSupervisor;
    @CsvCustomBindByName(column = "Basic Salary", required = true, converter = Converter.DecimalConverter.class)
    public BigDecimal basicSalary;
    @CsvCustomBindByName(column = "Rice Subsidy", required = true, converter = Converter.DecimalConverter.class)
    public BigDecimal riceSubsidy;
    @CsvCustomBindByName(column = "Phone Allowance", required = true, converter = Converter.DecimalConverter.class)
    public BigDecimal phoneAllowance;
    @CsvCustomBindByName(column = "Clothing Allowance", required = true, converter = Converter.DecimalConverter.class)
    public BigDecimal clothingAllowance;
    @CsvCustomBindByName(column = "Gross Semi-monthly Rate", required = true, converter = Converter.DecimalConverter.class)
    public BigDecimal grossSemimonthlyRate;
    @CsvCustomBindByName(column = "Hourly Rate", required = true, converter = Converter.DecimalConverter.class)
    public BigDecimal hourlyRate;

    /**
     * Returns a string representation of the employee.
     * The format is: "employeeId, firstName lastName, MM/dd/yyyy"
     * Where MM/dd/yyyy is the birthday formatted as month/day/year.
     * 
     * @return a string representation of the employee
     */
    @Override
    public String toString() {
        return String.format("%d, %s %s, %s", employeeId, firstName, lastName,
                birthday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }
}
