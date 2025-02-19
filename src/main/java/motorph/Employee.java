package motorph;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee {
    public int employeeId;
    public String lastName;
    public String firstName;
    public LocalDate birthday;

    public Employee(int employeeNumber, String lastName, String firstName, LocalDate birthday,
            String address, String phoneNumber, String sssNumber, String philhealthNumber, String tinNumber,
            String pagibigNumber, EmploymentStatus status, String position, String immediateSupervisor,
            BigDecimal basicSalary, BigDecimal riceSubsidy, BigDecimal phoneAllowance, BigDecimal clothingAllowance,
            BigDecimal grossSemimonthlyRate, BigDecimal hourlyRate) {
        this.employeeId = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;

    }

    @Override
    public String toString() {
        return String.format("%d, %s %s, %s", employeeId, firstName, lastName, birthday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }
}
