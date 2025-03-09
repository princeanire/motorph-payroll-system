package motorph;

import java.math.BigDecimal;

public class SalaryDeductionService {
    public static BigDecimal calculateSocialSecuritySystemContribution(BigDecimal compensation) {
        if (compensation.compareTo(BigDecimal.valueOf(3250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(3749.99)) <= 0) {
            return BigDecimal.valueOf(157.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(3750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(4249.99)) <= 0) {
            return BigDecimal.valueOf(180.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(4250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(4749.99)) <= 0) {
            return BigDecimal.valueOf(202.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(4750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(5249.99)) <= 0) {
            return BigDecimal.valueOf(225.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(5250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(5749.99)) <= 0) {
            return BigDecimal.valueOf(247.5);
        } else if (compensation.compareTo(BigDecimal.valueOf(5750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(6249.99)) <= 0) {
            return BigDecimal.valueOf(270.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(6250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(6749.99)) <= 0) {
            return BigDecimal.valueOf(292.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(6750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(7249.99)) <= 0) {
            return BigDecimal.valueOf(315.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(7250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(7749.99)) <= 0) {
            return BigDecimal.valueOf(337.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(7750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(8249.99)) <= 0) {
            return BigDecimal.valueOf(360.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(8250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(8749.99)) <= 0) {
            return BigDecimal.valueOf(382.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(8750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(9249.99)) <= 0) {
            return BigDecimal.valueOf(405.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(9250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(9749.99)) <= 0)
            return BigDecimal.valueOf(427.50);
        else if (compensation.compareTo(BigDecimal.valueOf(9750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(10249.99)) <= 0)
            return BigDecimal.valueOf(450.00);
        else if (compensation.compareTo(BigDecimal.valueOf(10250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(10749.99)) <= 0) {
            return BigDecimal.valueOf(472.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(10750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(11249.99)) <= 0) {
            return BigDecimal.valueOf(495.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(11250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(11749.99)) <= 0) {
            return BigDecimal.valueOf(517.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(11750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(12249.99)) <= 0) {
            return BigDecimal.valueOf(540.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(12250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(12749.99)) <= 0) {
            return BigDecimal.valueOf(562.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(12750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(13249.99)) <= 0) {
            return BigDecimal.valueOf(585.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(13250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(13749.99)) <= 0) {
            return BigDecimal.valueOf(607.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(13750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(14249.99)) <= 0) {
            return BigDecimal.valueOf(630.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(14250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(14749.99)) <= 0) {
            return BigDecimal.valueOf(652.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(14750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(15249.99)) <= 0) {
            return BigDecimal.valueOf(675.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(15250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(15749.99)) <= 0) {
            return BigDecimal.valueOf(697.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(15750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(16249.99)) <= 0) {
            return BigDecimal.valueOf(720.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(16250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(16749.99)) <= 0) {
            return BigDecimal.valueOf(742.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(16750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(17249.99)) <= 0) {
            return BigDecimal.valueOf(765.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(17250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(17749.99)) <= 0) {
            return BigDecimal.valueOf(787.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(17750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(18249.99)) <= 0) {
            return BigDecimal.valueOf(810.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(18250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(18749.99)) <= 0) {
            return BigDecimal.valueOf(832.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(18750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(19249.99)) <= 0) {
            return BigDecimal.valueOf(855.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(19250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(19749.99)) <= 0) {
            return BigDecimal.valueOf(877.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(19750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(20249.99)) <= 0) {
            return BigDecimal.valueOf(900.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(20250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(20749.99)) <= 0) {
            return BigDecimal.valueOf(922.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(20750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(21249.99)) <= 0) {
            return BigDecimal.valueOf(945.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(21250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(21749.99)) <= 0) {
            return BigDecimal.valueOf(967.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(21750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(22249.99)) <= 0) {
            return BigDecimal.valueOf(990.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(22250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(22749.99)) <= 0) {
            return BigDecimal.valueOf(1012.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(22750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(23249.99)) <= 0) {
            return BigDecimal.valueOf(1035.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(23250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(23749.99)) <= 0) {
            return BigDecimal.valueOf(1057.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(23750)) >= 0 && compensation.compareTo(BigDecimal.valueOf(24249.99)) <= 0) {
            return BigDecimal.valueOf(1080.00);
        } else if (compensation.compareTo(BigDecimal.valueOf(24250)) >= 0 && compensation.compareTo(BigDecimal.valueOf(24749.99)) <= 0) {
            return BigDecimal.valueOf(1102.50);
        } else if (compensation.compareTo(BigDecimal.valueOf(24750)) >= 0) return BigDecimal.valueOf(1125.00);

        else return BigDecimal.valueOf(135.00);
    }

    public static BigDecimal calculatePagIbigContribution(BigDecimal monthlyBasicSalary) {
        BigDecimal employeeContributionRate;
        BigDecimal employeePagIbigContribution = BigDecimal.valueOf(0);
        BigDecimal pagIbigMaximumContributionAmount = BigDecimal.valueOf(100.00);
        if (monthlyBasicSalary.compareTo(BigDecimal.valueOf(1500)) > 0) {
            employeeContributionRate = BigDecimal.valueOf(0.02);
            employeePagIbigContribution = monthlyBasicSalary.multiply(employeeContributionRate);

        } else if (monthlyBasicSalary.compareTo(BigDecimal.valueOf(1000)) >= 0 && monthlyBasicSalary.compareTo(BigDecimal.valueOf(1500)) <= 0) {
            employeeContributionRate = BigDecimal.valueOf(0.01);
            employeePagIbigContribution = monthlyBasicSalary.multiply(employeeContributionRate);
        }
        return BigDecimal.valueOf(Math.min(employeePagIbigContribution.doubleValue(), pagIbigMaximumContributionAmount.doubleValue()));
    }

    public static BigDecimal calculatePhilhealthContribution(BigDecimal monthlyBasicSalary) {
        BigDecimal philhealthPremiumRate = BigDecimal.valueOf(0.03);
        BigDecimal employeeShare = BigDecimal.valueOf(2);
        return monthlyBasicSalary.multiply(philhealthPremiumRate).divide(employeeShare);
    }
}
