package motorph;

import com.opencsv.bean.AbstractBeanField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class providing various data type converters for CSV parsing and processing.
 * 
 * <p>This class contains nested converter classes that extend OpenCSV's AbstractBeanField
 * to provide custom conversion logic for different data types. These converters are designed
 * to be used with OpenCSV's bean mapping functionality to transform string values from CSV files
 * into appropriate Java objects.</p>
 * 
 * <p>The following converters are provided:</p>
 * <ul>
 *   <li>{@link EmploymentStatusConverter} - Converts strings to EmploymentStatus enum values</li>
 *   <li>{@link TimeConverter} - Converts time strings to LocalTime objects with multiple format support</li>
 *   <li>{@link DateConverter} - Converts date strings in MM/dd/yyyy format to LocalDate objects</li>
 *   <li>{@link DecimalConverter} - Converts numeric strings with commas to BigDecimal objects</li>
 * </ul>
 * 
 * <p>Example usage with OpenCSV:</p>
 * <pre>
 * {@code
 * @CsvBindByName(column = "Status", converter = Converter.EmploymentStatusConverter.class)
 * private EmploymentStatus status;
 * 
 * @CsvBindByName(column = "StartTime", converter = Converter.TimeConverter.class)
 * private LocalTime startTime;
 * }
 * </pre>
 * 
 * @see com.opencsv.bean.AbstractBeanField
 * @see com.opencsv.bean.CsvBindByName
 */
public class Converter {
    /**
     * A custom converter for converting string values to {@link EmploymentStatus} enum instances.
     * This class is used with the OpenCSV library to handle the conversion during CSV parsing.
     * 
     * <p>This converter extends AbstractBeanField to provide specific conversion logic for
     * the EmploymentStatus enum type from String values found in CSV files.</p>
     * 
     * @see EmploymentStatus
     * @see com.opencsv.bean.AbstractBeanField
     */
    public static class EmploymentStatusConverter extends AbstractBeanField<EmploymentStatus, String> {

        @Override
        protected EmploymentStatus convert(String value) {
            return Enum.valueOf(EmploymentStatus.class, value);
        }
    }

    /**
     * A converter class that transforms String representations of time into LocalTime objects.
     * This converter is designed to work with the OpenCSV bean field processing framework.
     * <p>
     * The class attempts to parse time strings using multiple formats:
     * <ul>
     *   <li>H:mm - Single digit hour format (e.g., "9:30")</li>
     *   <li>HH:mm - Double digit hour format (e.g., "09:30")</li>
     * </ul>
     * <p>
     * If parsing fails with all available formats, a RuntimeException is thrown.
     * 
     * @see AbstractBeanField
     * @see LocalTime
     * @see DateTimeFormatter
     */
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

    /**
     * A custom converter for transforming date strings into LocalDate objects during CSV parsing.
     * 
     * <p>This class extends AbstractBeanField to provide conversion functionality
     * specifically for dates formatted as "MM/dd/yyyy" (e.g., "01/15/2023").</p>
     * 
     * <p>The converter uses a predefined DateTimeFormatter to parse input strings
     * into LocalDate objects, facilitating the automatic conversion of date fields
     * when mapping CSV data to Java beans.</p>
     */
    public static class DateConverter extends AbstractBeanField<LocalDate, String> {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        @Override
        protected LocalDate convert(String value) {
            return LocalDate.parse(value, DATE_FORMATTER);
        }
    }

    /**
     * A converter class for mapping CSV string values to BigDecimal objects.
     * This class extends the AbstractBeanField class from OpenCSV to handle
     * the conversion of string values that may contain commas as thousand separators
     * (e.g., "1,000.00") to BigDecimal objects.
     * 
     * <p>Use this converter in @CsvBindByName annotations to properly parse decimal
     * values from CSV files.</p>
     * 
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * @CsvBindByName(column = "Amount", converter = DecimalConverter.class)
     * private BigDecimal amount;
     * }
     * </pre>
     */
    public static class DecimalConverter extends AbstractBeanField<BigDecimal, String> {
        @Override
        protected BigDecimal convert(String value) {
            String sanitizedValue = value.replace(",", "");
            return new BigDecimal(sanitizedValue);
        }
    }
}