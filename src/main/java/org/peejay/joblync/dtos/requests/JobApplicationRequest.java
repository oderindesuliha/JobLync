package org.peejay.joblync.dtos.requests;

import lombok.Data;


    @Data
    public class JobApplicationRequest {
        private Long jobId;
        private Long applicantId;
    }