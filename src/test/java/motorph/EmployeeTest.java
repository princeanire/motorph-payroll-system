package motorph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeTest {

    @Test
    void displayEmployeeInformationInFormat() {
        // Arrange
        Employee antonioLim = new Employee(10002, "Lim", "Antonio",
                LocalDate.of(1988, 06, 19), "San Antonio De Padua 2, Block 1 Lot 8 and 2, Dasmarinas, Cavite",
                "171-867-411", "52-2061274-9",
                "331735646338", "683-102-776-000", "663904995411", EmploymentStatus.Regular,
                "Chief Operating Officer", "Garcia, Manuel III", new BigDecimal(60000), new BigDecimal(1500),
                new BigDecimal(2000), new BigDecimal(1000), new BigDecimal(30000), new BigDecimal(357.14));

        // Act
        String result = antonioLim.toString();

        // Assert
        assertEquals("10002, Antonio Lim, 06/19/1988", result);
    }

}
