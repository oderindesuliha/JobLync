package org.peejay.joblync.services;

import org.peejay.joblync.data.models.Applicant;
import org.springframework.data.repository.Repository;

interface ApplicantRepository extends Repository<Applicant, Long> {
}
