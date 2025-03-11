package motorph;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class SalaryDeductionServiceTest {
    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/sss-contribution-schedule.csv", numLinesToSkip = 1)
    public void calculateSocialSecuritySystemContributionTest(String minimumCompensation, String maximumCompensation, double contribution) {
        double parsedMinimumCompensation = Double.parseDouble(minimumCompensation.replace(",", ""));
        double parsedMaximumCompensation = Double.parseDouble(maximumCompensation.replace(",", ""));
        BigDecimal compensationLowerBound = BigDecimal.valueOf(parsedMinimumCompensation);
        BigDecimal compensationUpperBound = BigDecimal.valueOf(parsedMaximumCompensation);
        BigDecimal expectedSocialSecuritySystemContribution = BigDecimal.valueOf(contribution);

        assertEquals(expectedSocialSecuritySystemContribution, SalaryDeductionService.calculateSocialSecuritySystemContribution(compensationLowerBound));
        assertEquals(expectedSocialSecuritySystemContribution, SalaryDeductionService.calculateSocialSecuritySystemContribution(compensationUpperBound.subtract(BigDecimal.valueOf(0.01))));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/employee-basic-salary.csv", numLinesToSkip = 1)
    public void calculatePagIbigContributionTest(String employeeBasicSalary) {
        double parsedBasicSalary = Double.parseDouble(employeeBasicSalary.replace(",", ""));
        BigDecimal basicSalary = BigDecimal.valueOf(parsedBasicSalary);
        BigDecimal expectedPagIbigContribution = BigDecimal.valueOf(100.00);

        assertEquals(expectedPagIbigContribution, SalaryDeductionService.calculatePagIbigContribution(basicSalary));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/employee-philhealth-contributions.csv", numLinesToSkip = 1)
    public void calculatePhilhealthContributionTest(String employeeMonthlyBasicSalary, double philhealthContribution) {
        double parsedMonthlyBasicSalary = Double.parseDouble(employeeMonthlyBasicSalary.replace(",", ""));
        BigDecimal expectedPhilhealthContribution = BigDecimal.valueOf(philhealthContribution).stripTrailingZeros();

        assertEquals(expectedPhilhealthContribution, SalaryDeductionService.calculatePhilhealthContribution(BigDecimal.valueOf(parsedMonthlyBasicSalary)).stripTrailingZeros());
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/employee-withholding-tax.csv", numLinesToSkip = 1)
    public void calculateWithholdingTaxTest(String employeeMonthlyBasicSalary, double withholdingTax) {
        double parsedMonthlyBasicSalary = Double.parseDouble(employeeMonthlyBasicSalary.replace(",", ""));
        BigDecimal expectedWithholdingTax = BigDecimal.valueOf(withholdingTax);

        assertEquals(expectedWithholdingTax.stripTrailingZeros(), SalaryDeductionService.calculateWithholdingTax(BigDecimal.valueOf(parsedMonthlyBasicSalary)).stripTrailingZeros());


    }

}