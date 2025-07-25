package org.peejay.joblync.services;

import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;

public interface AdminService {
    UserRegisterResponse registerSubAdmin(SubAdminRequest request);
    void disableUser(String email);
    void enableUser(String email);
    void deleteUser(String email);
}
