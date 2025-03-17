package motorph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EmployeeDatabaseServiceTest {
    static Employee lizethVillegas;
    static List<AttendanceRecord> lizethVillegasAttendanceRecords;
    LocalTime logIn = LocalTime.of(8, 52, 0);
    LocalTime logOut = LocalTime.of(17, 23, 0);
    int workingHours = EmployeeDatabaseService.calculateEmployeeWorkingHours(this.logIn,
            this.logOut);

    @BeforeAll
    public static void getEmployeeDetailsAndAttendanceRecordsOfLizethVillegas() {
        EmployeeDatabaseService.getAllAttendanceRecords();
        lizethVillegas = EmployeeDatabaseService.getEmployeeDetailsByEmployeeId(10028).get();
        lizethVillegasAttendanceRecords = EmployeeDatabaseService
                .getAttendanceRecordsByEmployeeId(lizethVillegas.employeeId);
    }

    @Test
    public void calculateEmployeeWorkingHours() {
        int lizethVillegasWorkingHoursOnJun3 = 7;

        assertEquals(lizethVillegasWorkingHoursOnJun3, workingHours);
    }

    @Test
    public void calculateEmployeeWorkingHoursUsingAttendanceRecord() {
        LocalDate attendanceRecordDate = LocalDate.of(2024, 6, 3);
        AttendanceRecord attendanceRecord = new AttendanceRecord(lizethVillegas.employeeId,
                lizethVillegas.firstName, lizethVillegas.lastName,
                attendanceRecordDate, logIn, logOut);
        int lizethVillegasWorkingHoursOnJun3 = 7;

        int result = EmployeeDatabaseService.calculateEmployeeWorkingHours(attendanceRecord);

        assertEquals(lizethVillegasWorkingHoursOnJun3, result);
    }

    @Test
    public void calculateEmployeeDailyBasicPay() {
        BigDecimal lizethVillegasBasicPayOnJune3 = BigDecimal.valueOf(1000.02);

        BigDecimal lizethVillegasSampleBasicPay = EmployeeDatabaseService.calculateEmployeeDailyBasicPay(
                lizethVillegas.employeeId,
                workingHours);

        assertEquals(lizethVillegasBasicPayOnJune3, lizethVillegasSampleBasicPay);
    }

    @Test
    public void calculateEmployeeDeMinimisBenefits() {
        BigDecimal lizethVillegasDeMinimisBenefit = new BigDecimal(2500);

        BigDecimal lizethVillegasCalculatedDeMinimisBenefit = EmployeeDatabaseService
                .calculateEmployeeDeminimisBenefits(lizethVillegas.employeeId);

        assertEquals(lizethVillegasDeMinimisBenefit, lizethVillegasCalculatedDeMinimisBenefit);
    }

    @Test
    public void calculateEmployeeOvertimePay() {
        // Arrange
        LocalTime logIn = LocalTime.of(8, 29, 0);
        LocalTime logOut = LocalTime.of(19, 24, 0);
        BigDecimal overtimePay = new BigDecimal(0).stripTrailingZeros();
        LocalDate attendanceRecordDate = LocalDate.of(2024, 6, 7);
        AttendanceRecord attendanceRecordWithOvertimeHours = new AttendanceRecord(lizethVillegas.employeeId,
                lizethVillegas.firstName, lizethVillegas.lastName,
                attendanceRecordDate, logIn, logOut);

        BigDecimal result = EmployeeDatabaseService.calculateEmployeeOvertimePay(attendanceRecordWithOvertimeHours).stripTrailingZeros();

        assertEquals(overtimePay, result);
    }
}
