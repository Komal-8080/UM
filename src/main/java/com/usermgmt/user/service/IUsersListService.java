package com.usermgmt.user.service;

import com.usermgmt.user.dto.PermissionsDTO;
import com.usermgmt.user.dto.RegistrationDTO;
import com.usermgmt.user.dto.UsersListSummary;
import com.usermgmt.user.model.User;
import com.usermgmt.user.model.UserPermissions;

import java.util.List;
import java.util.UUID;

public interface IUsersListService {

    List<UsersListSummary> getUsersList(String token, long usersList);

    User editUserDetails(String token, RegistrationDTO registrationDTO);

    void removeUserDetails(String token);

    UserPermissions updatePermissions(String token, UUID permissionId, PermissionsDTO permissionsDTO);

    List<UsersListSummary> searchUser(String token, String search);
}
