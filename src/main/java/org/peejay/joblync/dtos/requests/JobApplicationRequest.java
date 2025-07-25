package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class JobApplicationRequest {
    @NotNull(message = "Job ID is required")
    private Long jobId;
    @NotNull(message = "Applicant ID is required")
    private Long applicantId;
}

