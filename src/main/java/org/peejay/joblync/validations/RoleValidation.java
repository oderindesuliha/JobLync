package org.peejay.joblync.validations;

import org.peejay.joblync.exceptions.InvalidRoleException;

public class RoleValidation {

    public static void validateRole(String role)  {
        if (!role.matches("^(ADMIN|APPLICANT|HR_MANAGER|RECRUITER|EMPLOYEE)$")) {
            throw new InvalidRoleException("Invalid role. Must be one of: ADMIN, APPLICANT, HR_MANAGER, RECRUITER, or EMPLOYEE.");
        }
    }
}
