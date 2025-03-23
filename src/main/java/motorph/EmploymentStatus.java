package motorph;

/**
 * This enum represents the employment status of employees at MotorPH.
 * It defines the possible employment statuses an employee can have within the organization.
 * 
 * <p>Available employment statuses:</p>
 * <ul>
 *   <li>{@code Regular} - Represents employees who have completed their probationary period and are now regular employees</li>
 *   <li>{@code Probationary} - Represents employees who are still within their probationary period</li>
 * </ul>
 * 
 * @see motorph.Employee
 */
public enum EmploymentStatus {
    /**
     * Represents a permanent employee who has completed the probationary period.
     * Regular employees are entitled to full benefits.
     */
    Regular,
    
    /**
     * Represents an employee who is still in the evaluation period.
     * Probationary employees may have limited benefits until becoming regular.
     */
    Probationary
}
