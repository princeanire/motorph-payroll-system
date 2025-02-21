package motorph;

import com.opencsv.bean.AbstractBeanField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Converter {
    public static class EmploymentStatusConverter extends AbstractBeanField<EmploymentStatus, String> {

        @Override
        protected EmploymentStatus convert(String value) {
            return Enum.valueOf(EmploymentStatus.class, value);
        }
    }

    public static class TimeConverter extends AbstractBeanField<LocalTime, String> {
        private static final DateTimeFormatter[] TIME_FORMATTERS = {
                DateTimeFormatter.ofPattern("H:mm"), // single digit hour
                DateTimeFormatter.ofPattern("HH:mm") // double digit hour
        };

        @Override
        protected LocalTime convert(String value) {
            for (DateTimeFormatter formatter : TIME_FORMATTERS) {
                try {
                    return LocalTime.parse(value, formatter);
                } catch (DateTimeParseException dateTimeParseException) {
                    continue;
                }
            }
            throw new RuntimeException("Unable to parse time: " + value);
        }
    }

    public static class DateConverter extends AbstractBeanField<LocalDate, String> {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        @Override
        protected LocalDate convert(String value) {
            return LocalDate.parse(value, DATE_FORMATTER);
        }
    }

    public static class DecimalConverter extends AbstractBeanField<BigDecimal, String> {
        @Override
        protected BigDecimal convert(String value) {
            String sanitizedValue = value.replace(",", "");
            return new BigDecimal(sanitizedValue);
        }
    }
}