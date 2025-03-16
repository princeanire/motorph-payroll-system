package motorph;

import com.opencsv.bean.AbstractBeanField;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Converter {

    /** Converts a String value to an EmploymentStatus Enum */
    public static class EmploymentStatusConverter extends AbstractBeanField<EmploymentStatus, String> {
        @Override
        protected EmploymentStatus convert(String value) {
            return Enum.valueOf(EmploymentStatus.class, value);
        }
    }

    /** Converts a String value to LocalTime, supporting multiple time formats */
    public static class TimeConverter extends AbstractBeanField<LocalTime, String> {
        private static final DateTimeFormatter[] TIME_FORMATTERS = {
            DateTimeFormatter.ofPattern("H:mm"),  // Single-digit hour format
            DateTimeFormatter.ofPattern("HH:mm")  // Double-digit hour format
        };

        @Override
        protected LocalTime convert(String value) {
            for (DateTimeFormatter formatter : TIME_FORMATTERS) {
                try {
                    return LocalTime.parse(value, formatter);
                } catch (DateTimeParseException ignored) {
                    // Continue trying other formats
                }
            }
            throw new RuntimeException("Unable to parse time: " + value);
        }
    }

    /** Converts a String value to LocalDate using the format MM/dd/yyyy */
    public static class DateConverter extends AbstractBeanField<LocalDate, String> {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        @Override
        protected LocalDate convert(String value) {
            return LocalDate.parse(value, DATE_FORMATTER);
        }
    }

    /** Converts a String value to BigDecimal, removing commas for proper parsing */
    public static class DecimalConverter extends AbstractBeanField<BigDecimal, String> {
        @Override
        protected BigDecimal convert(String value) {
            String sanitizedValue = value.replace(",", "");  // Remove commas from the value
            return new BigDecimal(sanitizedValue);
        }
    }
}
