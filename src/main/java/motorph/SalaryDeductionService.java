package motorph;

import java.math.BigDecimal;

public class SalaryDeductionService {

    /**
     * Calculates the Social Security System (SSS) contribution based on the
     * employee's compensation.
     * 
     * This method determines the appropriate SSS contribution amount by comparing
     * the employee's
     * compensation against predefined compensation brackets. Each bracket has a
     * specific
     * contribution amount associated with it.
     * 
     * For compensations below the minimum bracket (3250), a default contribution of
     * 135.00 is returned.
     * For compensations within a bracket, the corresponding contribution amount is
     * returned.
     * For compensations above the maximum defined bracket (24750+), a contribution
     * of 1125.00 is returned.
     * 
     * @param compensation The employee's compensation amount
     * @return The SSS contribution amount based on the compensation bracket
     */
    public static BigDecimal calculateSocialSecuritySystemContribution(BigDecimal compensation) {
        BigDecimal[][] brackets = {
                { BigDecimal.valueOf(3250), BigDecimal.valueOf(3749.99), BigDecimal.valueOf(157.50) },
                { BigDecimal.valueOf(3750), BigDecimal.valueOf(4249.99), BigDecimal.valueOf(180.00) },
                { BigDecimal.valueOf(4250), BigDecimal.valueOf(4749.99), BigDecimal.valueOf(202.50) },
                { BigDecimal.valueOf(4750), BigDecimal.valueOf(5249.99), BigDecimal.valueOf(225.00) },
                { BigDecimal.valueOf(5250), BigDecimal.valueOf(5749.99), BigDecimal.valueOf(247.50) },
                { BigDecimal.valueOf(5750), BigDecimal.valueOf(6249.99), BigDecimal.valueOf(270.00) },
                { BigDecimal.valueOf(6250), BigDecimal.valueOf(6749.99), BigDecimal.valueOf(292.50) },
                { BigDecimal.valueOf(6750), BigDecimal.valueOf(7249.99), BigDecimal.valueOf(315.00) },
                { BigDecimal.valueOf(7250), BigDecimal.valueOf(7749.99), BigDecimal.valueOf(337.50) },
                { BigDecimal.valueOf(7750), BigDecimal.valueOf(8249.99), BigDecimal.valueOf(360.00) },
                { BigDecimal.valueOf(8250), BigDecimal.valueOf(8749.99), BigDecimal.valueOf(382.50) },
                { BigDecimal.valueOf(8750), BigDecimal.valueOf(9249.99), BigDecimal.valueOf(405.00) },
                { BigDecimal.valueOf(9250), BigDecimal.valueOf(9749.99), BigDecimal.valueOf(427.50) },
                { BigDecimal.valueOf(9750), BigDecimal.valueOf(10249.99), BigDecimal.valueOf(450.00) },
                { BigDecimal.valueOf(10250), BigDecimal.valueOf(10749.99), BigDecimal.valueOf(472.50) },
                { BigDecimal.valueOf(10750), BigDecimal.valueOf(11249.99), BigDecimal.valueOf(495.00) },
                { BigDecimal.valueOf(11250), BigDecimal.valueOf(11749.99), BigDecimal.valueOf(517.50) },
                { BigDecimal.valueOf(11750), BigDecimal.valueOf(12249.99), BigDecimal.valueOf(540.00) },
                { BigDecimal.valueOf(12250), BigDecimal.valueOf(12749.99), BigDecimal.valueOf(562.50) },
                { BigDecimal.valueOf(12750), BigDecimal.valueOf(13249.99), BigDecimal.valueOf(585.00) },
                { BigDecimal.valueOf(13250), BigDecimal.valueOf(13749.99), BigDecimal.valueOf(607.50) },
                { BigDecimal.valueOf(13750), BigDecimal.valueOf(14249.99), BigDecimal.valueOf(630.00) },
                { BigDecimal.valueOf(14250), BigDecimal.valueOf(14749.99), BigDecimal.valueOf(652.50) },
                { BigDecimal.valueOf(14750), BigDecimal.valueOf(15249.99), BigDecimal.valueOf(675.00) },
                { BigDecimal.valueOf(15250), BigDecimal.valueOf(15749.99), BigDecimal.valueOf(697.50) },
                { BigDecimal.valueOf(15750), BigDecimal.valueOf(16249.99), BigDecimal.valueOf(720.00) },
                { BigDecimal.valueOf(16250), BigDecimal.valueOf(16749.99), BigDecimal.valueOf(742.50) },
                { BigDecimal.valueOf(16750), BigDecimal.valueOf(17249.99), BigDecimal.valueOf(765.00) },
                { BigDecimal.valueOf(17250), BigDecimal.valueOf(17749.99), BigDecimal.valueOf(787.50) },
                { BigDecimal.valueOf(17750), BigDecimal.valueOf(18249.99), BigDecimal.valueOf(810.00) },
                { BigDecimal.valueOf(18250), BigDecimal.valueOf(18749.99), BigDecimal.valueOf(832.50) },
                { BigDecimal.valueOf(18750), BigDecimal.valueOf(19249.99), BigDecimal.valueOf(855.00) },
                { BigDecimal.valueOf(19250), BigDecimal.valueOf(19749.99), BigDecimal.valueOf(877.50) },
                { BigDecimal.valueOf(19750), BigDecimal.valueOf(20249.99), BigDecimal.valueOf(900.00) },
                { BigDecimal.valueOf(20250), BigDecimal.valueOf(20749.99), BigDecimal.valueOf(922.50) },
                { BigDecimal.valueOf(20750), BigDecimal.valueOf(21249.99), BigDecimal.valueOf(945.00) },
                { BigDecimal.valueOf(21250), BigDecimal.valueOf(21749.99), BigDecimal.valueOf(967.50) },
                { BigDecimal.valueOf(21750), BigDecimal.valueOf(22249.99), BigDecimal.valueOf(990.00) },
                { BigDecimal.valueOf(22250), BigDecimal.valueOf(22749.99), BigDecimal.valueOf(1012.50) },
                { BigDecimal.valueOf(22750), BigDecimal.valueOf(23249.99), BigDecimal.valueOf(1035.00) },
                { BigDecimal.valueOf(23250), BigDecimal.valueOf(23749.99), BigDecimal.valueOf(1057.50) },
                { BigDecimal.valueOf(23750), BigDecimal.valueOf(24249.99), BigDecimal.valueOf(1080.00) },
                { BigDecimal.valueOf(24250), BigDecimal.valueOf(24749.99), BigDecimal.valueOf(1102.50) },
                { BigDecimal.valueOf(24750), BigDecimal.valueOf(Double.MAX_VALUE), BigDecimal.valueOf(1125.00) }
        };

        for (BigDecimal[] bracket : brackets) {
            if (compensation.compareTo(bracket[0]) >= 0 && compensation.compareTo(bracket[1]) <= 0) {
                return bracket[2];
            }
        }
        return BigDecimal.valueOf(135.00);
    }

    /**
     * Calculates the Pag-IBIG Fund contribution based on the employee's monthly
     * basic salary.
     * 
     * The contribution rate is determined as follows:
     * - 1% of monthly basic salary if the salary is ≤ ₱1,500
     * - 2% of monthly basic salary if the salary is > ₱1,500
     * 
     * The maximum contribution amount is capped at ₱100.00 regardless of salary.
     * 
     * @param monthlyBasicSalary The employee's monthly basic salary
     * @return The Pag-IBIG contribution amount based on the salary and capped at
     *         ₱100.00
     */
    public static BigDecimal calculatePagIbigContribution(BigDecimal monthlyBasicSalary) {
        BigDecimal contributionRate = (monthlyBasicSalary.compareTo(BigDecimal.valueOf(1500)) > 0)
                ? BigDecimal.valueOf(0.02)
                : BigDecimal.valueOf(0.01);
        return monthlyBasicSalary.multiply(contributionRate).min(BigDecimal.valueOf(100.00));
    } // simplified the logic for getting contributions

    /**
     * Calculates the employee's PhilHealth contribution based on monthly basic salary.
     * The computation applies a 3% rate of the monthly basic salary, and then divides
     * the result by 2 to get the employee's share (as the employer pays the other half).
     * 
     * @param monthlyBasicSalary The employee's monthly basic salary
     * @return The employee's PhilHealth contribution amount
     */
    public static BigDecimal calculatePhilhealthContribution(BigDecimal monthlyBasicSalary) {
        return monthlyBasicSalary.multiply(BigDecimal.valueOf(0.03)).divide(BigDecimal.valueOf(2));
    } // made the division step explicit

    /**
     * Calculates the withholding tax based on the provided monthly salary following the Philippine tax brackets.
     * 
     * Tax Brackets:
     * - 0 to 20,832: 0% tax
     * - 20,833 to 33,332: 20% on excess over 20,833
     * - 33,333 to 66,666: 2,500 + 25% on excess over 33,333
     * - 66,667 to 166,666: 10,833 + 30% on excess over 66,667
     * - 166,667 to 666,666: 40,833.33 + 32% on excess over 166,667
     * - 666,667 and above: 200,833.33 + 35% on excess over 666,667
     *
     * @param monthlySalary the employee's monthly salary as a BigDecimal value
     * @return the calculated withholding tax amount as a BigDecimal
     */
    public static BigDecimal calculateWithholdingTax(BigDecimal monthlySalary) {
        BigDecimal withholdingTax = BigDecimal.valueOf(0);
        if (monthlySalary.compareTo(BigDecimal.valueOf(0)) > 0
                && monthlySalary.compareTo(BigDecimal.valueOf(20832)) <= 0) {
            withholdingTax = BigDecimal.valueOf(0);
        } else if (monthlySalary.compareTo(BigDecimal.valueOf(20833)) >= 0
                && monthlySalary.compareTo(BigDecimal.valueOf(33333)) < 0) {
            withholdingTax = monthlySalary.subtract(BigDecimal.valueOf(20833)).multiply(BigDecimal.valueOf(0.20));
        } else if (monthlySalary.compareTo(BigDecimal.valueOf(33333)) >= 0
                && monthlySalary.compareTo(BigDecimal.valueOf(66667)) < 0) {
            withholdingTax = monthlySalary.subtract(BigDecimal.valueOf(33333)).multiply(BigDecimal.valueOf(0.25));
            withholdingTax = withholdingTax.add(BigDecimal.valueOf(2500));
        } else if (monthlySalary.compareTo(BigDecimal.valueOf(66667)) >= 0
                && monthlySalary.compareTo(BigDecimal.valueOf(166667)) < 0) {
            withholdingTax = monthlySalary.subtract(BigDecimal.valueOf(66667)).multiply(BigDecimal.valueOf(0.30));
            withholdingTax = withholdingTax.add(BigDecimal.valueOf(10833));

        } else if (monthlySalary.compareTo(BigDecimal.valueOf(166667)) >= 0
                && monthlySalary.compareTo(BigDecimal.valueOf(666667)) < 0) {
            withholdingTax = monthlySalary.subtract(BigDecimal.valueOf(166667)).multiply(BigDecimal.valueOf(0.32));
            withholdingTax = withholdingTax.add(BigDecimal.valueOf(40833.33));

        } else if (monthlySalary.compareTo(BigDecimal.valueOf(666667)) >= 0) {
            withholdingTax = monthlySalary.subtract(BigDecimal.valueOf(666667)).multiply(BigDecimal.valueOf(0.35));
            withholdingTax = withholdingTax.add(BigDecimal.valueOf(200833.33));
        }
        return withholdingTax;
    }
}
